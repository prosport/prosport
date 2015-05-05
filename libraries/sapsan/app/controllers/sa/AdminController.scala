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



/**Model.scala:108
 * Основной контроллер админки
 */
object AdminController extends Controller with Secured {

//    case class exportForm(format: String, model: String, selCol: Set[String], returnTo: String, sendData: String, csvSeparator, charset: String)
//    val exportFormMapping = Form(
//        mapping()(exportForm.apply)(exportForm.unapply)
//    )

    /** Название приложения */
    val appName = Play.application.configuration.getString("sapsan.name", "Demo")

    /** Левое навигационное меню */
    def navigation = Schema.models.map(m => m._1 -> m._2.label)

    /** Приветственная страница админки, включает список моделей и краткую историю по ним */
    def index = withAuth { username => implicit request =>
        val i18lErrors = Schema.prepareKeysForI18l(true).trim.nonEmpty
        val warningsBlock = admin.index.selfVerification(i18lErrors, false)
        Ok(admin.index.siteAdministration(warningsBlock))
    }


    /** Список записей данной модели, включает разбивку на страницы и сортировку */
    def list(model: String, page: Int, sort: String, orderBy: String) = withAuth { _ => implicit request =>
        val m = Schema.models(model)

        // записи для грида
        val itemsPerPage = Play.application.configuration.getInt("sapsan.pagination.items_per_page")
        val sortBy = if (sort.isEmpty) m.nameField.name else sort
        //val orderBy = if(order) "asc" else "desc"
        //val items = Base.page(page, itemsPerPage, sortBy, orderBy)
        val items = m.pageXYZ(page, itemsPerPage, sortBy, orderBy)
        Ok(admin.list.list(Schema.models(model), items))
    }

    /** Форма добавления новой записи в заданную модель */
    def create(model: String) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        // Каша: используется хелпер из Java-части фреймворка
        val f = Form.form(m.clazz)

        Ok(admin.edit.recordCreate(m, f))
    }

    /** Добавление новой записи - обработка данных отправленных create() */
    def save(model: String) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        if(request.body.isInstanceOf[AnyContentAsFormUrlEncoded]) {
            saveFormUrlEncoded(m, request.body)
        } else if(request.body.isInstanceOf[AnyContentAsMultipartFormData]) {
            request.body.asMultipartFormData match {
                case Some(form) => {
                    println(form.files.size)
                    form.files
                    Ok(form.file(m.allFieldsFileUpload.values.head.name).head.ref.file.length.toString)
                    saveFormUrlEncoded(m, request.body)
                }
                case None => BadRequest
            }
        } else {
            Logger.warn("Strange type of request body = " + request.body)
            BadRequest
        }
    }

    def saveFormUrlEncoded(m: Model, form: AnyContent/*, files: Seq[FilePart] = Seq()*/) = form.asFormUrlEncoded match {
        case Some(form) =>
            val data = form.map(x => x._1 -> x._2.head)
            import collection.JavaConversions._
            val f = Form.form(m.clazz).bind(data)

            if (f.hasErrors()) {
                BadRequest(admin.edit.recordCreate(m, f))
            } else {
                m.saveRecord(f.get)

                redirectAfterSave(m.toCNotation, m.extractId(f.get), data, "success" -> Messages("interface.successAdded",  f.get.toString))
            }
        case None => BadRequest
    }


    /** Перенаправление после редактирования записи в модели, в зависимости от нажатой кнопки в форме */
    def redirectAfterSave(model: String, id: Long, data: Map[String, String], msg: (String, String) ) =
        if (data.exists(_._1 == FormButton.SaveAndEdit.toString))
            Redirect(controllers.sa.routes.AdminController.edit(model, id)).flashing(msg)
        else if (data.exists(_._1 == FormButton.SaveAndAdd.toString))
            Redirect(controllers.sa.routes.AdminController.create(model)).flashing(msg)
        else
            Redirect(controllers.sa.routes.AdminController.list(model)).flashing(msg)


    /** Форма редактирования существующей записи данной модели */
    def edit(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        val f = Form.form(classOf[Any]).fill(m.recordById(id))
        Ok(admin.edit.recordEdit(m, f, id))
    }

    /** Обновление записи - обработка данных отправленных edit() */
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

    /** Просмотр подробной информации о выбранной записи */
    def show(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        Ok(admin.show.index(m, m.recordById(id), id))
    }

    /** Страница подтверждения удаления записи */
    def deleteConfirm(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        Ok(admin.delete.confirm(m, m.recordById(id), id))
    }

    /** Реальное удаление записи */
    def delete(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        m.delete(id)
        Redirect(routes.AdminController.list(m.toCNotation))
    }

    /** Настройка экспорта данных из модели */
    def exportConfig(model: String) = withAuth { _ => implicit request =>
        Ok(admin.export.index(Schema.models(model)))
    }

    /** Экспорт данных из данной модели */
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

    /** История редактирования записей в данной модели */
    def history(model: String) = withAuth { _ => implicit request =>
        Ok(admin.history.model(Schema.models(model)))
    }

    /** История редактирования данной записи */
    def historyRecord(model: String, id: Long) = withAuth { _ => implicit request =>
        val m = Schema.models(model)
        Ok(admin.history.record(m, m.recordById(id), id))
    }

    /** Массовые действия с переданными записями */
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