package controllers;

import models.ProductCategory;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NavNode;
import utils.StaticNavigation;
import views.html.catalog;
import views.html.index;
import views.html.login;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import static play.data.Form.form;

public class Application extends Controller {

    public static class Login {

        public String email;
        public String password;

        //        @Transactional(readOnly = true)
        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    public static Result index() {
        return ok(index.render());
    }

    public static Result catalog() {
        return ok(catalog.render());
    }

    public static Result listCategories() {
        return ok(Arrays.toString(getNavigation().toArray()));
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
//        return redirect(
//                routes.Application.login()
//        );
        return ok("");
    }

    public static SortedSet<NavNode> getNavigation() {

        List<ProductCategory> roots = ProductCategory.findAllRoots();
        SortedSet<NavNode> catalogNavigation = StaticNavigation.convert(roots);

        NavNode catalog = NavNode.root("Каталог", "#", 2);
        catalog.nodes.addAll(catalogNavigation);
        SortedSet<NavNode> staticNavigation = StaticNavigation.get();
        staticNavigation.add(catalog);
        return staticNavigation;
    }




}
