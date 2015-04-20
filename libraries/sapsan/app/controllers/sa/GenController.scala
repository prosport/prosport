package controllers.sa

import controllers.sa.AdminController._
import play.api.mvc._
import sapsan.schema.Schema


object GenController extends Controller with Secured {

  /** Заполнение моделей базы данных случайными данными */
  def random = withAuth { _ => implicit request =>
    List("base", "section", "question", "answer", "template" //, "letter", "bases_acces"
    ).foreach(m => Schema.models(m).createRandomRecord)
    //        Array("Привет", "Ля-ля", "На-на").foreach( g => new Group(g).save())
    Ok("Модели заполнены случайными данными")
  }

  def i18l = withAuth { _ => implicit request =>
    Ok(Schema.prepareKeysForI18l)
  }

}