package controllers.sa

import play.api.mvc._
import views.html.sapsan._
import sapsan.schema.Schema
import play.data._
import play.Play
import play.api.i18n.Messages
import java.nio.charset.Charset
import sapsan.common.Export


object FormButton extends Enumeration {
    type FormButton = Value
    val Save = Value("_save")
    val SaveAndAdd = Value("_saveAndAdd")
    val SaveAndEdit = Value("_saveAndEdit")
}

/**
 * Основной контроллер админки
 */
object Admin extends Controller with Secured {

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
        Ok(admin.index.siteAdministration())
    }

    /** Заполнение моделей базы данных случайными данными */
    def random = withAuth { _ => implicit request =>
        List("base", "section", "question", "answer", "template" //, "letter", "bases_acces"
        ).foreach(m => Schema.models(m).createRandomRecord)
        //        Array("Привет", "Ля-ля", "На-на").foreach( g => new Group(g).save())
        Ok("Модели заполнены случайными данными")
    }


    /** Список записей данной модели, включает разбивку на страницы и сортировку */
    def list(model: String, page: Int, sort: String, orderBy: String) = withAuth { _ => implicit request =>
//        x(request.queryString)
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
        request.body.asFormUrlEncoded match {
            case Some(form) =>
                val m = Schema.models(model)
                val data = form.map(x => x._1 -> x._2.head)
                import collection.JavaConversions._
                val f = Form.form(m.clazz).bind(data)

                if (f.hasErrors()) {
                    BadRequest(admin.edit.recordCreate(m, f))
                } else {
                    m.saveRecord(f.get)

                    redirectAfterSave(m.toCNotation,  m.extractId(f.get), data, "success" -> Messages("interface.successAdded",  f.get.toString))
                }
            case None => BadRequest
        }
    }

    /** Перенаправление после редактирования записи в модели, в зависимости от нажатой кнопки в форме */
    def redirectAfterSave(model: String, id: Long, data: Map[String, String], msg: (String, String) ) =
        if (data.exists(_._1 == FormButton.SaveAndEdit.toString))
            Redirect(controllers.sa.routes.Admin.edit(model, id)).flashing(
                msg
            )
        else if (data.exists(_._1 == FormButton.SaveAndAdd.toString))
            Redirect(controllers.sa.routes.Admin.create(model)).flashing(
                msg
            )
        else
            Redirect(controllers.sa.routes.Admin.list(model)).flashing(
                msg
            )


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
        Redirect(routes.Admin.list(m.toCNotation))
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

    def x(qs: Map[String, Seq[String]]) = {
        val pattern = """f\[(\w+)\]\[(\d+)\]\[(\w)\]""".r
        qs.filter(_._1.startsWith("f")).foreach {
            f =>
                val pattern(name, id, t) = f._1
                println(name)
        }
    }

}