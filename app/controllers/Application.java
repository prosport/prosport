package controllers;

import models.Product;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import javax.persistence.TypedQuery;
import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
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
