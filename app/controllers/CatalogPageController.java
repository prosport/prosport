package controllers;

import models.Product;
import models.ProductCategory;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import utils.LogMessageStrings;
import utils.Navigation;
import views.html.catalog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 5/20/15T
 */
public class CatalogPageController extends Controller {

    public static Result catalog(String fullUrl) {
        ProductCategory category;
        if (Navigation.CATALOG_URL.equals(fullUrl)) { // "Каталог" -> перенаправляем на самого левого сына
            category = ProductCategory.getLeftmostRoot();
        } else {
            String url = getLastSubUrl(fullUrl);
            category = ProductCategory.findByUrl(url);// Ищем категорию по урлу
        }

        if (category == null) {
            Logger.warn(LogMessageStrings.NO_CATEGORY_WITH_NAME + fullUrl);
            return ok(catalog.render(Navigation.CATALOG, new ArrayList<>(0)));
        }

        ProductCategory leftDeepestSon = category.getLeftDeepestChildrenOrSelf();
        if (leftDeepestSon != category) {
            return redirect(leftDeepestSon.getFullUrl());
        } else {
            List<Product> products = Product.getProductsWithCategory(category.name);
            return ok(catalog.render(category.name, products));
        }
    }

    private static String getLastSubUrl(String fullUrl) {
        return fullUrl.substring(fullUrl.lastIndexOf('/') + 1);
    }
}
