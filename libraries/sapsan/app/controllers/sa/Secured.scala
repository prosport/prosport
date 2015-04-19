package controllers.sa

import play.api.mvc._


trait Secured {

    def username(request: RequestHeader) = Some("valera") //request.session.get(Security.username)

    def onUnauthorized(request: RequestHeader) = Results.Redirect("/") //Results.Redirect(routes.Auth.login)

    def withAuth(f: => String => Request[AnyContent] => Result) = {
        Security.Authenticated(username, onUnauthorized) {
            user =>
                Action(request => f(user)(request))
        }
    }

    //    /**
    //     * This method shows how you could wrap the withAuth method to also fetch your user
    //     * You will need to implement UserDAO.findOneByUsername
    //     */
    //    def withUser(f: User => Request[AnyContent] => Result) = withAuth { username => implicit request =>
    //        User.find.where("")
    //        UserDAO.findOneByUsername(username).map { user =>
    //            f(user)(request)
    //        }.getOrElse(onUnauthorized(request))
    //    }
}