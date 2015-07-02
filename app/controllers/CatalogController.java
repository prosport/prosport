package controllers;

import models.Product;
import models.ProductCategory;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import utils.LogMessageStrings;
import views.html.catalog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 5/20/15T
 */
public class CatalogController extends Controller {

    public static Result catalog(String categoryUrl) {
        //TODO: любая нефинальная категория должна редиректить на первую категорию

        ProductCategory category = ProductCategory.findByUrl(categoryUrl);
        if(category == null) {
            Logger.warn(LogMessageStrings.NO_CATEGORY_WITH_URL + categoryUrl);
            return ok(catalog.render("Каталог", new ArrayList<>(0)));
        }
        List<Product> products = Product.getProductsWithCategory(category.name);
        return ok(catalog.render(category.name, products));
    }
}
