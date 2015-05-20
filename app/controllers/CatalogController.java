package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by andy on 5/20/15.
 */
public class CatalogController extends Controller {

    public static Result getProducts(String categoryName) {
        Logger.debug(categoryName);
        return ok("ok!");

    }
}
