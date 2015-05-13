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

import java.util.*;

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

    public static Collection<NavNode> getNavigation() {
        Set<NavNode> staticNavigation = StaticNavigation.get();

        Set<NavNode> catalogNavigation = new HashSet<>();
        List<ProductCategory> roots = ProductCategory.findAllRoots();
        for(ProductCategory root : roots) {
            NavNode item = putChild(root, null);
            catalogNavigation.add(item);
        }

        NavNode catalog = NavNode.root("Каталог", "#");
        catalog.nodes.addAll(catalogNavigation);
        staticNavigation.remove(catalog);
        staticNavigation.add(catalog);
        return staticNavigation;
    }

    private static NavNode putChild(ProductCategory child, NavNode root) {
        NavNode item = NavNode.leaf(child.name, child.url);
        if(root != null) root.nodes.add(item);
        for(ProductCategory c : child.getSubCategories()) {
            putChild(c, item);
        }
        return item;
    }


}
