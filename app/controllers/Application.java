package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import models.Product;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import static play.data.Form.form;
import views.html.*;
import javax.persistence.TypedQuery;
import java.util.List;

public class Application extends Controller {

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    public static Result index() {
        User u  = User.find.where().eq("email", "valera.dt@gmail.com").findUnique();
        return ok(u.password);
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

    @Transactional(readOnly = true)
    public static Result test() {
        Logger.info("Query product...");
        Logger.debug("test!!");
        TypedQuery<Product> query = JPA.em().createQuery("FROM Product", Product.class);
        List<Product> products = query.getResultList();

        return ok(index.render("products count: " + products.size()));

    }


}
