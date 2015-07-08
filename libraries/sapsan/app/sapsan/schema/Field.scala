package sapsan.schema


import sapsan.common.{HtmlInputComponent, Notation}
import play.api.i18n.Messages
import sapsan.annotation.SapsanField
import sapsan.common.Notation
import java.lang.reflect.{Field => JavaField}
import play.Logger
import java.text.SimpleDateFormat
import java.util.Date
import scala.util.Random
import com.avaje.ebean.Ebean

object Field {
    val id = Schema.conf.getString("sapsan.column_names.id", "id")
    val name = Schema.conf.getString("sapsan.column_names.name", "name")
    val createdAt = Schema.conf.getString("sapsan.column_names.createdAt", "createdAt")
    val updatedAt = Schema.conf.getString("sapsan.column_names.createdAt", "updatedAt")
    val dateFields = Array(createdAt, updatedAt)
}

class Field(val model: Model, jf: JavaField) {
    import HtmlInputComponent._
    val typesToComponents = Map(
        DataTypeGroup.Unknown -> Unknown,
        DataTypeGroup.Boolean -> InputCheckbox,
        DataTypeGroup.Integer -> InputNumeric,
        DataTypeGroup.Float -> InputNumeric,
        DataTypeGroup.String -> InputText,
        DataTypeGroup.Text -> TextArea,
        DataTypeGroup.Password -> InputPassword,
        DataTypeGroup.Timestamp -> InputDateTime,
        DataTypeGroup.Blob -> FileUpload,
        DataTypeGroup.Enumerated -> InputRadio,
        DataTypeGroup.OneToMany -> Select,
        DataTypeGroup.Primary -> Label,
//        DataTypeGroup.OneToOne ->,
        DataTypeGroup.ManyToOne -> InputText
//        DataTypeGroup.ManyToMany ->,
    )


    //                val numericPrecision: Int = 0,
    //                val numericScale: Int = 0,
    //                val extra: String = "",
    //                val k: String = "",

    /** Internal name in application */
    val name = jf.getName

    /** Native data type */
    val nt: Any = jf.getType

    /** Name for user interface */
    lazy val label = {
      val key = s"field.${model.name}.${name}"
      if (Messages.isDefinedAt(key)) Messages(key)
      else name
    }

    /** Name in C-nonation (for use as identifiers within site) */
    val toCNotation = Notation.camelToC(name)

    val ann = jf.getAnnotation(classOf[SapsanField])

    /** Generalized data type */
    lazy val dataType =
        if(ann.dataType() == DataTypeGroup.Unknown) detectDataType
        else ann.dataType()

    lazy val component =
        if(ann.inputComponent() == HtmlInputComponent.Unknown) typesToComponents(dataType)
        else ann.inputComponent()

    /** Maximum length of characters, that can be written to this field */
    lazy val maxLength = {
        val ebeanLen = jf.getAnnotation(classOf[com.avaje.ebean.validation.Length])
        val playLen = jf.getAnnotation(classOf[play.data.validation.Constraints.MaxLength])
        if (ebeanLen != null) ebeanLen.max()
        else if (playLen != null) playLen.value()
        else 0
    }

    /** Minimum length of characters, that can be written to this field */
    lazy val minLength = {
        val ebeanLen = jf.getAnnotation(classOf[com.avaje.ebean.validation.Length])
        val playLen = jf.getAnnotation(classOf[play.data.validation.Constraints.MinLength])
        if (ebeanLen != null) ebeanLen.min()
        else if (playLen != null) playLen.value()
        else 0
    }


    /** The minimum numerical/time value allowed in the field */
    //TODO : Option[Long]
    lazy val min = {
        val a = jf.getAnnotation(classOf[play.data.validation.Constraints.Min])
        val range = jf.getAnnotation(classOf[com.avaje.ebean.validation.Range])
        if (a != null) a.value()
        else if (range != null) range.min()
        else null
    }

    /** The maximum numerical/time value allowed in the field  */
    lazy val max = {
        val a = jf.getAnnotation(classOf[play.data.validation.Constraints.Max])
        val range = jf.getAnnotation(classOf[com.avaje.ebean.validation.Range])
        if (a != null) a.value()
        else if (range != null) range.max()
        else null
    }

    /** Helper for date-formatting */
    lazy val dateFormatter = {
        val defaultFormat = Schema.conf.getString("sapsan.localization.default_full_date_pattern", "dd.MM.yyyy HH:mm:ss")
        val format = jf.getAnnotation(classOf[play.data.format.Formats.DateTime])
        new SimpleDateFormat(if (format != null) format.pattern() else defaultFormat)
    }

    private[this] lazy val columnAnn = jf.getAnnotation(classOf[javax.persistence.Column])

    //val unq = jf.getAnnotation(classOf[Unique])
    lazy val isUnique = if (columnAnn == null) false else columnAnn.unique()

    private[this] lazy val regexpAnn = jf.getAnnotation(classOf[play.data.validation.Constraints.Pattern])
    lazy val regexp = if (regexpAnn == null) "" else regexpAnn.value()
    lazy val regexpError = if (regexpAnn == null) "" else regexpAnn.message()

    lazy val isNullable = (jf.getAnnotation(classOf[javax.validation.constraints.NotNull]) == null) ||
        (jf.getAnnotation(classOf[play.data.format.Formats.NonEmpty]) == null) ||
        (jf.getAnnotation(classOf[play.data.validation.Constraints.Required]) == null)

    lazy val isEmail = (jf.getAnnotation(classOf[play.data.validation.Constraints.Email]) != null)

    lazy val isTransient = (jf.getAnnotation(classOf[javax.persistence.Transient]) != null)

    lazy val isTimestampCreated = jf.getAnnotation(classOf[com.avaje.ebean.annotation.CreatedTimestamp]) != null

    lazy val isTimestampUpdated =
      jf.getAnnotation(classOf[com.avaje.ebean.annotation.UpdatedTimestamp]) != null ||
      jf.getAnnotation(classOf[javax.persistence.Version]) != null


    // GeneratedValue
    lazy val isPrimary = jf.getAnnotation(classOf[javax.persistence.Id]) != null
    lazy val isAutoIncrement = isPrimary

    lazy val isOneToOne = jf.getAnnotation(classOf[javax.persistence.OneToOne]) != null
    lazy val isOneToMany = jf.getAnnotation(classOf[javax.persistence.OneToMany]) != null
    lazy val isManyToOne = jf.getAnnotation(classOf[javax.persistence.ManyToOne]) != null
    lazy val isManyToMany = jf.getAnnotation(classOf[javax.persistence.ManyToMany]) != null
    lazy val isKeyField = isOneToOne || isOneToMany || isManyToOne || isManyToMany
    lazy val isEnumerated = jf.getAnnotation(classOf[javax.persistence.Enumerated]) != null
    lazy val encryptedAnn = jf.getAnnotation(classOf[com.avaje.ebean.annotation.Encrypted])

    /** types */
    def isInt8 = nt == classOf[Byte] || nt == classOf[java.lang.Byte]

    def isInt16 = nt == classOf[Short] || nt == classOf[java.lang.Short]

    def isInt32 = nt == classOf[Int] || nt == classOf[java.lang.Integer]

    def isInt64 = nt == classOf[Long] || nt == classOf[java.lang.Long]

    def isIntAny = isInt8 || isInt16 || isInt32 || isInt64

    /** Floats (IEEE 754) */
    def isFloat32 = nt == classOf[Float] || nt == classOf[java.lang.Float]

    def isFloat64 = nt == classOf[Double] || nt == classOf[java.lang.Double]

    def isFloatAny = isFloat32 || isFloat64

    def isBoolean = nt == classOf[Boolean] || nt == classOf[java.lang.Boolean]

    def isChar = nt == classOf[Char]

    def isString = nt == classOf[String] || nt == classOf[StringBuffer] || nt == classOf[StringBuilder] || nt == classOf[CharSequence]

    def isTimestampAny = nt == classOf[java.util.Date] || nt == classOf[java.sql.Time] || nt == classOf[java.sql.Timestamp]

    def detectDataType: DataTypeGroup = nt match {
        case dt if columnAnn != null && columnAnn.columnDefinition().toLowerCase.contains("text") => DataTypeGroup.Text
        case dt if (encryptedAnn != null || name.toLowerCase == "password") => DataTypeGroup.Password
        case dt if isOneToOne => DataTypeGroup.OneToOne
        case dt if isOneToMany => DataTypeGroup.OneToMany
        case dt if isManyToOne => DataTypeGroup.ManyToOne
        case dt if isManyToMany => DataTypeGroup.ManyToMany
        case dt if isEnumerated => DataTypeGroup.Enumerated
        case dt if isBoolean => DataTypeGroup.Boolean
        case dt if isIntAny => DataTypeGroup.Integer
        case dt if isFloatAny => DataTypeGroup.Float
        case dt if isString => DataTypeGroup.String
        case dt if isTimestampAny => DataTypeGroup.Timestamp
        case nt => {
            Logger.warn("Unknown type:" + nt)
            DataTypeGroup.String
        }
    }


    // JoinColumn/JoinTable
    def foreignModelName = Notation.camelToC(jf.getType.getSimpleName)

    def foreignModel = Schema.models(foreignModelName)

    def foreignColumn = foreignModel.primaryField

    def isAutoFilling = dataType == DataTypeGroup.Timestamp && Field.dateFields.contains(name)

    def hasDefaultValue = defaultValue != null

    def defaultValue = extract(model.newInstance)

    /** Extracts this field from given object */
    def extract(obj: Any) = {
        try {
            jf.get(obj)
        } catch {
            case e: IllegalAccessException => throw new IllegalAccessException(s"""Access denied for field "${name}" at model "${model.name}": """ + e.getMessage)
            case x: Exception => throw x
        }
    }

    /** as extract() but after formats data  */
    def extractD(obj: Any) = jf.get(obj) match {
        case v: java.lang.Boolean => if (v) Messages("interface.yes") else Messages("interface.no")
        case v: java.util.Date => dateFormatter.format(v)
        case v: String => v.take(50)
        case v => {
            //println(v + "=" + v.getClass)
            v
        }
    }


    def fromString(v: String) = nt match {
        case t: Date => {
            val format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            format.parse(v.replace('T', ' '))
        }
        case t if isManyToOne => foreignModel.recordById(v.toLong)
        case dt if isBoolean => Array("on", "true", "yes").contains(v.toLowerCase)
        case t => v
    }

    /** Converts Long to native (only integers) type of field. */
    def fromLong(id: Long): Any = nt match {
        case dt if isInt64 => id
        case dt if isInt32 => id.toInt
        case dt if isInt16 => id.toShort
        case dt if isInt8 => id.toByte
        case t => id
    }

    /** Is cyclic relation, ie this field has M-1 key to this model (commonly used for trees) */
    def isCyclicRel = isManyToOne && foreignModel == model

    /** Generates random value for this field */
    def random = nt match {
        case nt if isBoolean => Random.nextBoolean
        case nt if isInt8 => Random.nextInt(Byte.MaxValue).toByte
        case nt if isInt16 => Random.nextInt(Short.MaxValue).toShort
        case nt if isInt32 => Random.nextInt
        case nt if isInt64 => Random.nextLong
        case nt if isFloat32 => Random.nextFloat
        case nt if isFloat64 => Random.nextDouble
        case nt if isTimestampAny => new Date
        case nt if isString => model + "-" + Random.nextInt
        case nt if isManyToOne => {
            val ids = Ebean.find(foreignModel.clazz).findIds()
            if (ids.isEmpty) throw new Exception(s"Table $name must be contains records!")
            val id = ids.get(Random.nextInt(ids.size()))
            foreignModel.recordById(id)
        }

        case nt => "Unknown"
    }


}