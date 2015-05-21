package controllers.sa

import play.api.mvc._
import views.html.sapsan._
import sapsan.schema.{Schema, Model}
import play.data._
import play.Play
import play.api.i18n.Messages
import java.nio.charset.Charset
import sapsan.common.Export
import sapsan.common.FormButton
import play.api.Logger



/**
 * General Controller of SapsanAdmin - implements CRUD-actions
 */
object AdminController extends Controller with Secured {

//    case class exportForm(format: String, model: String, selCol: Set[String], returnTo: String, sendData: String, csvSeparator, charset: String)
//    val exportFormMapping = Form(
//        mapping()(exportForm.apply)(exportForm.unapply)
//    )

    /** Name of web-project */
    val appName = Play.application.configuration.getString("sapsan.name", "Demo")

    /** Side navigation bar */
    def navigation = Schema.models.map(m => m._1 -> m._2.label)

    /** Welcome page of SapsanAdmin, includes list of models and brief information about them */
    def index = withAuth { username => implicit request =>
        Logger.debug(username)
        val i18lErrors = Schema.prepareKeysForI18l(true).trim.nonEmpty
        val warningsBlock = admin.index.selfVerification(i18lErrors, false)
        Ok(admin.index.siteAdministration(warningsBlock))
    }


    /** Record list of this model, includes pagination and sorting */
    def list(model: String, page: Int, sort: String, orderBy: String) = withAuth { _ => implicit request =>
        val m = Schema.models(model)

        val itemsPerPage = Play.application.configuration.getInt("sapsan.pagination.items_per_page")
        val sortBy = if (sort.isEmpty) m.nameField.name else sort
        //val orderBy = if(order) "asc" else "desc"
        //val items = Base.page(page, itemsPerPage, sortBy, orderBy)
        val items = m.pageXYZ(page, itemsPerPage, sortBy, orderBy)
        Ok(admin.list.list(Schema.models(model), items))
    }

    /** Displays the form of adding new record to a given model  */
    def create(model: String) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        // TODO: bad mixed code, ше used helper from Play-Java
        val f = Form.form(m.clazz)

        Ok(admin.edit.recordCreate(m, f))
    }

    /** Processes form data and creates new record. This method handle data from create() method */
    def save(model: String) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        if(request.body.isInstanceOf[AnyContentAsFormUrlEncoded]) {
            request.body.asFormUrlEncoded match {
                case Some(form) => saveFormUrlEncoded(m, form)
                case None => BadRequest
            }
        } else if(request.body.isInstanceOf[AnyContentAsMultipartFormData]) {
            request.body.asMultipartFormData match {
                case Some(form) => {

                    val files = m.uploadAndSaveFiles(form.files).toMap

                    saveFormUrlEncoded(m, form.asFormUrlEncoded, files)

                }
                case None => BadRequest
            }
        } else {
            Logger.warn("Strange type of request body = " + request.body)
            BadRequest
        }
    }

    def saveFormUrlEncoded(m: Model, form: Map[String, Seq[String]], preload: Map[String, String] = Map()) = {
        val data = form.map(x => x._1 -> x._2.head) ++ preload
        import collection.JavaConversions._
        val f = Form.form(m.clazz).bind(data)

        if (f.hasErrors()) {
            BadRequest(admin.edit.recordCreate(m, f))
        } else {
            m.saveRecord(f.get)
            redirectAfterSave(m.toCNotation, m.extractId(f.get), data, "success" -> Messages("interface.successAdded",  f.get.toString))
        }
    }


    /** Redirect after editing record, this depends on button pressed in form */
    def redirectAfterSave(model: String, id: Long, data: Map[String, String], msg: (String, String) ) =
        if (data.exists(_._1 == FormButton.SaveAndEdit.toString))
            Redirect(controllers.sa.routes.AdminController.edit(model, id)).flashing(msg)
        else if (data.exists(_._1 == FormButton.SaveAndAdd.toString))
            Redirect(controllers.sa.routes.AdminController.create(model)).flashing(msg)
        else
            Redirect(controllers.sa.routes.AdminController.list(model)).flashing(msg)


    /** Displays editing form of existing record of this model */
    def edit(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        val f = Form.form(classOf[Any]).fill(m.recordById(id))
        Ok(admin.edit.recordEdit(m, f, id))
    }

    /** Processes form data and update existing record. This method handle data from edit() method */
    def update(model: String, id: Long) = withAuth { _ => implicit request =>
        request.body.asFormUrlEncoded match {
            case Some(form) =>
                val m = Schema.models(model)

                val data = form.filter(_._1 != "_save").map(x => x._1 -> x._2.head)
                import collection.JavaConversions._
                val f = Form.form(m.clazz).bind(data)

                if (f.hasErrors()) {
                    BadRequest(admin.edit.recordEdit(m, f, id))
                } else {

                    m.updateRecord(f.get, id)
                    redirectAfterSave(m.toCNotation,  m.extractId(f.get), data, "success" -> Messages("interface.successEdited",  f.get.toString))

                }
            case None => BadRequest
        }
    }

    /** Displays detailed information about this record */
    def show(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        Ok(admin.show.index(m, m.recordById(id), id))
    }

    /** Page for confirm deleting record */
    def deleteConfirm(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        Ok(admin.delete.confirm(m, m.recordById(id), id))
    }

    /** Deletes record from database */
    def delete(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        m.delete(id)
        Redirect(routes.AdminController.list(m.toCNotation))
    }

    /** Shows export settings's form data from given model */
    def exportConfig(model: String) = withAuth { _ => implicit request =>
        Ok(admin.export.index(Schema.models(model)))
    }

    /** Export data from given model and send file to user */
    def export(model: String, all: Boolean) = withAuth { _ => implicit request =>
        request.body.asFormUrlEncoded match {
            case Some(form) =>
                val charset = Charset.forName(form("charset").head)
                val format = form("format").head
                val model = form("model").head
                val selCol = form("sel_col").toSet
                val returnTo = form("return_to").head
                val csvSeparator = form("csv_separator").head
                val sendData = form("send_data").head.toBoolean
                val result = Export.toCSV(model, selCol, charset, csvSeparator)
                println(result)

                //text/javascript
                //"text/csv"
                //text/xml
                //text/yaml
                Ok(result).withHeaders(
                    CONTENT_TYPE -> "text/csv",
                    CONTENT_DISPOSITION -> s"attachment;filename=$model-export.$format"
                )
            case None => BadRequest
        }
    }

    /** History editing records of this model */
    def history(model: String) = withAuth { _ => implicit request =>
        Ok(admin.history.model(Schema.models(model)))
    }

    /** History of editing this record */
    def historyRecord(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        Ok(admin.history.record(m, m.recordById(id), id))
    }

    /** Mass actions with given recods (codes records are transmitted via POST-method) */
    def bulkAction(model: String) = withAuth { _ => implicit request =>
        request.body.asFormUrlEncoded match {
            case Some(form) =>
                println(form)
                println(form.get("bulk_ids[]"))
                println(form.get("bulk_action").head)
                Ok
            case None =>
                BadRequest
        }
    }

}