package controllers.sa


import play.api.data.Forms._
import play.api.data._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.i18n.Messages
import play.api.mvc._
import sapsan.schema.Schema


object AuthController extends Controller {

    case class LoginData(email: String, password: String, rememberMe: Boolean, redirect: String)

    val loginForm = Form(
        mapping(
            "email" -> nonEmptyText,
            "password" -> text,
            "rememberMe" -> boolean,
            "redirect" -> text
        )(LoginData.apply)(LoginData.unapply) verifying(Messages("admin.auth.badLogin"), result => result match {
            case userData => check(userData.email, userData.password)
        })
    )

    def check(username: String, password: String) = {
        // TODO checks in everywhere
        val modelName = Schema.conf.getString("sapsan.authorization.model", "user").toLowerCase
        val methodName = Schema.conf.getString("sapsan.authorization.method", "authenticate").toLowerCase
        val model = Schema.models(modelName)

        try {
            val method = model.clazz.getMethod(methodName, classOf[String], classOf[String])
            val user = method.invoke(null, username, password)
            user != null
        } catch {
            case e: Throwable => e.printStackTrace()
            false
        }
    }

    /** Shows login form */
    def login(redirect: String) = Action { implicit request =>
        val emptyFormWithRedirect = Map("redirect" -> redirect)
        Ok(views.html.sapsan.auth.login(loginForm.bind(emptyFormWithRedirect)))
    }

    /** User authentication - data processing from login () */
    def authenticate = Action { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => BadRequest(views.html.sapsan.auth.login(formWithErrors)),
            user => (
                  if(user.redirect.nonEmpty) { println(user.redirect); Redirect(user.redirect) }
                  else Redirect(routes.AdminController.index)
              ).withSession(Security.username -> user.email)
        )
    }

    def logout = Action {
        Redirect(routes.AdminController.index).withNewSession.flashing(
            "success" -> Messages("admin.auth.logoutSuccess")
        )
    }
}