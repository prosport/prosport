package sapsan.schema

import java.lang.reflect.{Field => ReflectField}

import sapsan.annotation.SapsanField
import play.api.i18n.Messages
import scala.collection.mutable.LinkedHashMap
import sapsan.common.Notation
import com.avaje.ebean.Ebean
import java.util.Date

class Model(val clazz: Class[_]) extends Ordered[Model] {

    /** Название класса */
    val name = clazz.getSimpleName

    /** Название для пользователей */
    lazy val label = {
      val key = "model." + name
      if(Messages.isDefinedAt(key)) Messages(key)
      else name
    }


    /** JPA-аннотация модели @Table (описывает свойства таблицы) */
    private [this] lazy val tableAnn = clazz.getAnnotation(classOf[javax.persistence.Table])

    /** Название таблицы в базе данных */
    lazy val table = if(tableAnn == null) Notation.camelToC(name) else tableAnn.name()

    /** Название схемы в базе данных */
    lazy val schema = if(tableAnn == null) "" else tableAnn.schema()

    /** Ключи уникальности для данной модели */
    lazy val uniqueConstraints = if(tableAnn == null) List() else tableAnn.uniqueConstraints()

    /**  Является ли наследником класса play.db.ebean.Model */
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


    /** Название в Си-нотации (для применения в виде идентификаторов на сайте) */
    val toCNotation = Notation.camelToC(name)

    /** Экспериментальный экземпляр объекта для данной модели (для получения значений по-умолчанию и других операций) */
    def experiment = clazz.getConstructor().newInstance()

//    init

//    /** Инициализация класса */
//    def init = {
//        // TODO getDeclaredFields
//        clazz.getFields.foreach { f =>
//            // Берём только ПУБЛИЧНЫЕ поля, помеченные аннотацией Label
//            if(f.getAnnotation(classOf[Label]) != null) {
//                val field = new Field(this, f)
//                fields.put(field.toCNotation, field)
//            }
//        }
//
//    }

    /** Ассоциативный массив из названий полей в кач. ключей и пустых строк - как значений. Магия 7 уровня, объяснения в книгах по алхимии. */
    val emptyForm = fields.map { case (_, f) => (f.toCNotation, "") }

    //    def fieldsWithoutKeys = fields.filter(f => !(f._2.isId || f._2.isKeyField))

    /** Только простые поля для формы редактирования, без первичных и внешних ключей, авто-поставляемых */
    def fieldsForEdit = fields.filter(f => !(f._2.isPrimary /* || f._2.isKeyField || f._2.isDefaultExists */ || f._2.isOneToMany ))
    def fieldsForEdit2 = fieldsForEdit.filter(f => !(f._2.isDefaultExists))
    def fieldsForEdit3 = fieldsForEdit.filter(f => !(Field.dateFields.contains(f._2.name)))

    /** Поля для вывода в списке записей */
    def fieldsForGrid = fields.filter(f => !(f._2.isManyToMany || f._2.isOneToMany))

    val maxGridColumns = 6

    def hasPrimary = fields.exists(_._2.isPrimary)
    /** Возвращает первую колонку, которая является первичным ключом */
    //TODO hasPrimary
    def primaryField = fields.find(_._2.isPrimary).headOption match {
      case Some(f) => f._2
      case None => fields.head._2
    }

    /** Возвращает "именной ключ", или же первый столбец, если именной ключ не будет найден */
    def nameField = {
        fields.find(f => f._2.name == Field.name).headOption.getOrElse {
            fields.find(f => f._2.dataType == DataTypeGroup.String).headOption.getOrElse {
                fields.head
            }
        }
    }._2

    /** Извлечение кода записи из переданного объекта */
    def extractId(obj: Any) = primaryField.extract(obj).toString.toLong

    /** Формирование ассоциативного массива: (код записи -> именное поле) */
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

    /** Загрузка экземпляра объекта данной модели по его коду */
    def recordById(id: Any) = Ebean.find(clazz).where.eq(primaryField.name, id).findUnique()

    /** Добавление новой записи в БД по карте,
      * где ключом выступает имя поля в модели, а значением - некоторая строка
      */
    def createRecord(data : Map[String, String] ) {
        val obj = clazz.getConstructor().newInstance()

        // Установка полям модели значений из формы
        data.foreach { case (key, value) =>
                val f = clazz.getDeclaredField(key)
                val mf = fields(key)
                f.set(obj, mf.fromString(value))
//                println(s"$key = ${value}")
        }

        saveRecord(obj)
    }

    /** Создание записи в БД, заполненной случайными данными */
    def createRandomRecord {
        val obj = clazz.getConstructor().newInstance()

        // Установка полям модели значений из формы
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

    /** Сохранение новой записи  в БД */
    def saveRecord(bean: Any) {
        // Проставляем дату добавления записи (если есть такое поле)
        getReflectField(Field.createdAt).map { f =>
            f.set(bean, new Date())
        }
        // Сохраняем запись
        try {
            val method = clazz.getMethod("save")
            method.invoke(bean)
        } catch {
            case _: Throwable => Ebean.save(bean)
        }
    }

    /** Обновление записи  в БД */
    def updateRecord(bean: Any, id: Long) {
        // Проставляем дату обновления записи (если есть такое поле)
        getReflectField(Field.updatedAt).map { f =>
            f.set(bean, new Date())
        }
        // Обновляем запись
        try {
            val method = clazz.getMethod("update", classOf[Object])
            val nativeId = primaryField.fromLong(id)
            method.invoke(bean, nativeId.asInstanceOf[Object])  //TODO !!! id.toString.toInt.asInstanceOf[Object]
        } catch {
            case e: Throwable =>
                e.printStackTrace()
                // => Ebean.update(bean)
            throw e
        }
    }

//    def appendHistoryEvent(de) = {
//
//    }

    /** Абсолютно статическая, то есть связи M-1 и 1-1 отсутствуют, но цикличные связи возможны
      * т.е. ищем первую связанную, но не цикличную связь */
    lazy val isStatic = fields.find(f => (f._2.isManyToOne  && !f._2.isCyclicRel)).isEmpty

    /** Является ли данная модель связанной с переданной связью 1-М со стороны "M"  **/
    def isSlave(that: Model) = fields.find(f => f._2.isManyToOne && f._2.foreignModel == that).nonEmpty

    /** Сравнение модели по степени статичности */
    def compare(that: Model) =
        if(isSlave(that)) -1
        else if(that.isSlave(this)) 1
        else 0

    /** Количество записей в таблице */
    def recordCount = Ebean.find(clazz).findRowCount()
//    def recordCount = {
//      val qb = JPA.em().getCriteriaBuilder
//      val cq = qb.createQuery(classOf[Long])
//      cq.select(qb.count(cq.from(clazz)))
//      JPA.em().createQuery(cq).getSingleResult
//    }
//    def recordCount = 0L // TODO Hibernate

    def delete(id : Long) = {
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


  // удаление без загрузки
  //        Ebean.delete(m.clazz, id)


  // удаление через вызов метода delete()
  //        val method = m.clazz.getMethod("delete")
  //        method.invoke(r)
  // перевод к списку


    override def toString: String = name
}