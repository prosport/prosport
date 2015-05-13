package sapsan.schema


import play.Play
import play.api.i18n.Messages
import play.libs.Classpath

import scala.collection.immutable.TreeMap


object Schema {
  lazy val conf = Play.application.configuration

  /** Mas of models (key - C-name of each model) */
  lazy val models = loadModels(conf.getString("sapsan.models_package", "models"))

  def modelsByStatic = models.values.toList.sortWith(_ < _)

  /** Scans specified package and loads models to map */
  def loadModels(packageName: String) = {
    import scala.collection.JavaConversions._
    val models = Classpath.getTypesAnnotatedWith(Play.application, packageName, classOf[javax.persistence.Entity]).map { className =>
      val clazz = Class.forName(className, true, Play.application.classloader)
      val model = new Model(clazz)
      (model.toCNotation, model)
    }
      //.filter(_._2.isModel)

    // Sorts models by keys
    TreeMap(models.toSeq: _*)
  }


  def prepareKeysForI18l(onlyNew: Boolean = true) = {
    val lineSeparator = "\n" + ("#" * 80) + "\n"

    lineSeparator +
    models.map{ case(_, m) =>
      val modelName = s"model.${m.name}"
      modelName + "\n" +
        m.fields.map { case(_, f) =>
          val fieldName = s"field.${m.name}.${f.name}"
          if(onlyNew)
            if(Messages.isDefinedAt(fieldName)) None
            else Some(fieldName)
          else Some(fieldName)
        }.flatMap(e => e).mkString("=\n")
    }.mkString (lineSeparator)
  }



}