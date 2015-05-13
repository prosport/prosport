package controllers.sa

import play.api.mvc._
import sapsan.schema.Schema


object GenController extends Controller with Secured {

  /** Filling models database with random data */
  def random(numberAddingEntries: Integer) = withAuth { _ => implicit request =>
    // TODO Need implementation
    List("base", "section", "question", "answer", "template")
      .foreach(m => Schema.models(m).createRandomRecord)

    Ok("Models are filled with random data")
  }

  def i18l(onlyNew: Boolean) = withAuth { _ => implicit request =>
    Ok(Schema.prepareKeysForI18l(onlyNew))
  }

  def unitTests = withAuth { _ => implicit request =>
    Ok(views.html.sapsan.gen.easyUnitTests())
  }
}