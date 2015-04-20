package controllers.sa

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.Messages

object AuthController extends Controller {

    val loginForm = Form(
        tuple(
            "email" -> nonEmptyText,
            "password" -> text
        ) verifying(Messages("auth.badLogin"), result => result match {
            case (email, password) => check(email, password)
        })
    )

    def check(username: String, password: String) = (username == "admin" && password == "admin")

    /** Форма входа в приложение */
    def login = Action {
        implicit request =>
        //        Ok(auth.login(play.data.Form loginForm))
            Ok
    }

    /** Авторизация пользователя - обработка данных из login() */
    def authenticate = Action {
        implicit request =>
        //        loginForm.bindFromRequest.fold(
        //            formWithErrors => BadRequest(auth.login(formWithErrors)),
        //            user => Redirect(routes.AdminController.index).withSession(Security.username -> user._1)
        //        )
            Ok
    }

    def logout = Action {
        Redirect(routes.AdminController.index).withNewSession.flashing(
            "success" -> Messages("auth.logoutSuccess")
        )
    }
}