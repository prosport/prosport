package sapsan.schema

import java.io.File
import java.lang.reflect.{Field => ReflectField}

import play.{Logger, Play}
import play.api.libs.Files
import play.api.mvc.MultipartFormData
import sapsan.annotation.SapsanField
import play.api.i18n.Messages
import scala.collection.mutable.LinkedHashMap
import sapsan.common.{UploadUtils, HtmlInputComponent, Notation}
import com.avaje.ebean.Ebean
import java.util.Date

class Model(val clazz: Class[_]) extends Ordered[Model] {

    /** Model name */
    val name = clazz.getSimpleName

    /** Name for web interface */
    lazy val label = {
      val key = "model." + name
      if(Messages.isDefinedAt(key)) Messages(key)
      else name
    }


    /** JPA-annotation @Table for this model */
    private [this] lazy val tableAnn = clazz.getAnnotation(classOf[javax.persistence.Table])

    /** Name of table in database */
    lazy val table = if(tableAnn == null) Notation.camelToC(name) else tableAnn.name()

    /** Schema name of table in database */
    lazy val schema = if(tableAnn == null) "" else tableAnn.schema()

    lazy val uniqueConstraints = if(tableAnn == null) List() else tableAnn.uniqueConstraints()

    //TODO Hibernate http://stackoverflow.com/questions/1042798/retrieving-the-inherited-attribute-names-values-using-java-reflection
    val isModel =
      clazz.getAnnotation(classOf[javax.persistence.Entity]) != null &&
      clazz.getFields.size > 1 //&&
      hasPrimary

    def getAllFields(c: Class[_], fields: Array[ReflectField] = Array() ) : Array[ReflectField]  =
        if(c.getSuperclass == null) fields ++ c.getDeclaredFields.filter(isNeedField _)
        else fields ++ getAllFields(c.getSuperclass, c.getDeclaredFields.filter(isNeedField _))

    def isNeedField(f: ReflectField) = f.getAnnotation(classOf[SapsanField]) != null

    lazy val fields = getAllFields(clazz).map{ f =>
        val field = new Field(this, f)
        (field.toCNotation, field)
    }.toMap


    val toCNotation = Notation.camelToC(name)

    /** Experimental object instance for the model (for default values and other operations) */
    def newInstance = clazz.getConstructor().newInstance()

    /** An associative array of field names in Kutch. Keys and blank lines - both values. Magic level 7, the explanations in books on alchemy. */
    val emptyForm = fields.map { case (_, f) => (f.toCNotation, "") }

    //    def fieldsWithoutKeys = fields.filter(f => !(f._2.isId || f._2.isKeyField))

    /** Only simple form fields for editing, without primary, foreign keys and auto-supplied */
    def fieldsForEdit = fields.filter(f => !(f._2.isPrimary /* || f._2.isKeyField || f._2.isDefaultExists */ || f._2.isOneToMany ))
    def fieldsForEdit2 = fieldsForEdit.filter(f => !(f._2.hasDefaultValue))
    def fieldsForEdit3 = fieldsForEdit.filter(f => !(Field.dateFields.contains(f._2.name)))

    /** selected fields for grid */
    def fieldsForGrid = fields.filter(f => !(f._2.isManyToMany || f._2.isOneToMany))

    val maxGridColumns = 6

    def hasPrimary = fields.exists(_._2.isPrimary)

    /** Returns first fields, that is primary key */
    def primaryField = fields.find(_._2.isPrimary).headOption match {
      case Some(f) => f._2
      case None => fields.head._2
    }

    def nameField = {
        fields.find(f => f._2.name == Field.name).headOption.getOrElse {
            fields.find(f => f._2.dataType == DataTypeGroup.String).headOption.getOrElse {
                fields.head
            }
        }
    }._2

    /** Extracts record ID from given object */
    def extractId(obj: Any) = primaryField.extract(obj).toString.toLong

    /** Creates has map: (record code -> naming field) */
    def biList = {
        val result = new LinkedHashMap[Any, Any]
        val items = Ebean.find(clazz)
            .orderBy(nameField.name)
            .findList()

        import collection.JavaConversions._
        for(item <- items) {
            result.put(primaryField.extract(item), nameField.extract(item))
        }
        result
    }

    /** Loads an instance record of this model by id */
    def recordById(id: Any) = Ebean.find(clazz).where.eq(primaryField.name, id).findUnique()

    /** Creates new record to database by map data */
    def createRecord(data : Map[String, String] ) {
        val obj = clazz.getConstructor().newInstance()

        data.foreach { case (key, value) =>
                val f = clazz.getDeclaredField(key)
                val mf = fields(key)
                f.set(obj, mf.fromString(value))
        }

        saveRecord(obj)
    }

    /** Creates new record and fills it random data */
    def createRandomRecord {
        val obj = clazz.getConstructor().newInstance()

        fieldsForEdit2.foreach { case (name, field) =>
            val f = clazz.getDeclaredField(field.name)
            f.set(obj, field.random)
        }

        saveRecord(obj)
    }

    def getReflectField(name: String) = {
        try {
            val f = clazz.getField(name)
            Some(f)
        } catch {
            case _ : NoSuchFieldException => None
        }
    }

    /** Saves new record into database */
    def saveRecord(bean: Any) {
        getReflectField(Field.createdAt).map { f =>
            f.set(bean, new Date())
        }

//        invokeMethod(bean, "save") { Ebean.save(bean) }
        try {
            val method = clazz.getMethod("save")
            method.invoke(bean)
        } catch {
            case _: Throwable => Ebean.save(bean)
        }
    }

    /** Updates existing record by record-object and id */
    def updateRecord(bean: Any, id: Long) {
        getReflectField(Field.updatedAt).map { f =>
            f.set(bean, new Date())
        }

//        val nativeId = primaryField.fromLong(id)
//        println(nativeId)
//        invokeMethod(bean, "update", nativeId) {}
        try {
            val method = clazz.getMethod("update")
            val nativeId = primaryField.fromLong(id)
            method.invoke(bean, nativeId.asInstanceOf[Object])  //TODO !!! id.toString.toInt.asInstanceOf[Object]
        } catch {
            case e: Throwable => Ebean.update(bean)
        }
    }


    def invokeMethod(obj: Any, methodName: String, args: Any*)(whenError: => Unit) = {
        try {
            val method = clazz.getMethod(methodName)
            method.invoke(obj, args)
        } catch {
            case e: Throwable =>
                whenError
                Logger.error(s"Error invokeMethod, model: $name, method: $methodName, args: $args." + e.getLocalizedMessage)
//                throw e
        }
    }

//    def appendHistoryEvent(de) = {
//
//    }

    /** Is absolutely static, ie communication M-1 and 1-1 are not available, but cyclic communication is possible */
    lazy val isStatic = fields.find(f => (f._2.isManyToOne  && !f._2.isCyclicRel)).isEmpty

    /** Is the model associated with the transferred communication "1-M" from the "M"  **/
    def isSlave(that: Model) = fields.find(f => f._2.isManyToOne && f._2.foreignModel == that).nonEmpty

    /** Comparison of the model for much static */
    def compare(that: Model) =
        if(isSlave(that)) -1
        else if(that.isSlave(this)) 1
        else 0

    /** Count records in this table */
    def recordCount = Ebean.find(clazz).findRowCount()
//    def recordCount = {
//      val qb = JPA.em().getCriteriaBuilder
//      val cq = qb.createQuery(classOf[Long])
//      cq.select(qb.count(cq.from(clazz)))
//      JPA.em().createQuery(cq).getSingleResult
//    }
//    def recordCount = 0L // TODO Hibernate

    // TODO   val method = m.clazz.getMethod("delete") &&  method.invoke(r)
    // TODO check and invoke methods model - save(), update(), delete()

    def delete(id : Long) = {
        //        Ebean.delete(m.clazz, id)
      val r = recordById(id)
      Ebean.delete(r)
    }


  def pageXYZ(page: Int, pageSize: Int, sortBy: String, orderBy: String = "asc") = {
    Ebean.find(clazz).where
      .orderBy(sortBy + " " + orderBy)
      .findPagingList(pageSize)
      .setFetchAhead(false)
      .getPage(page)
  }


    def allFieldsFileUpload = fields.filter(_._2.component == HtmlInputComponent.FileUpload)

    def isExistFieldFileUpload = allFieldsFileUpload.nonEmpty


    def uploadAndSaveFiles(files: Seq[MultipartFormData.FilePart[Files.TemporaryFile]]) = {
        val relativePath = Play.application.configuration.getString("sapsan.upload.path", "media")
        val absolutePath = Play.application.getFile(relativePath)
        for (file <- files) yield {
            val start = System.currentTimeMillis
            val relativePath = UploadUtils.uploadAndSaveFile(file.ref, file.filename, absolutePath)
            Logger.debug(s"Times of loading file ${System.currentTimeMillis - start} ms.")
            file.key -> relativePath
        }
    }

    override def toString: String = name
}