package controllers;

import dao.DAOHelper;
import models.ProductCategory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.tree;

import java.util.List;

/**
 * Created by andy on 4/17/15.
 */
public class TreePageController extends Controller {

    @Transactional(readOnly = true)
    public static Result GET() {
        List<ProductCategory> categories = DAOHelper.getAll(ProductCategory.class);
        return ok(tree.render("Дерево", categories));
    }

}
