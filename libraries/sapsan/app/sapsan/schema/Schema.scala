package sapsan.schema


import play.Play
import play.libs.Classpath

import scala.collection.immutable.TreeMap


object Schema {

  /** Хеш по Си-псевдониму имени */
  lazy val models = loadModels(Play.application.configuration.getString("sapsan.models_package", "models"))


  def modelsByStatic = models.values.toList.sortWith(_ < _)

  /**
   * Загружает модели в память.
   * @param packageName название пакета. Модели будут загружены рекурсивно из него и всех подкаталогов
   */
  def loadModels(packageName: String) = {
    import scala.collection.JavaConversions._
    val models = Classpath.getTypesAnnotatedWith(Play.application, packageName, classOf[javax.persistence.Entity]).map { className =>
      val clazz = Class.forName(className, true, Play.application.classloader)
      val model = new Model(clazz)
      (model.toCNotation, model)
    }
      //.filter(_._2.isModel)

    // Отсортируем модели по ключам, по алфавиту
    TreeMap(models.toSeq: _*)
  }


  def prepareKeysForI18l = models.map{ case(_, m) =>
    "#=================\n" +
    s"model.${m.name}=\n" +
    m.fields.map { case(_, f) =>
        s"field.${m.name}.${f.name}=\n"
    }.mkString
  }.mkString


//  println(prepareKeysForI18l)
}