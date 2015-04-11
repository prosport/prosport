package controllers;

import dao.DAOHelper;
import models.ProductCategory;
import models.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static class Login {

        public String email;
        public String password;

        @Transactional(readOnly = true)
        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    @Transactional(readOnly = true)
    public static Result index() {
        User u  = User.authenticate("email", "valera.dt@gmail.com");
        return u == null
                ? notFound("User with this email does'nt exist")
                : ok(u.getPassword());
    }

    @Transactional(readOnly = true)
    public static Result listCategories() {
        List<ProductCategory> pc = DAOHelper.getAll(ProductCategory.class);
        return ok(Json.toJson(pc));
    }

    /**
     * Login page.
     */
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(""); ///TODO
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }


}
