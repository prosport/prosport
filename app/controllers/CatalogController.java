package controllers;

import models.ProductCategory;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NavNode;
import views.html.catalog;

/**
 * Created by andy on 5/20/15.
 */
public class CatalogController extends Controller {

    public static Result catalog(String categoryUrl) {
        ProductCategory category = ProductCategory.findByUrl(categoryUrl);
        return ok(catalog.render(category == null ? "Каталог" : category.name));
    }
}
