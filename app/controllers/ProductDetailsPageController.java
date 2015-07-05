package controllers;

import models.Product;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.productDetails;


/**
 * Created by andy on 03.07.15.
 */
public class ProductDetailsPageController extends Controller {

    public static Result getProductDetailsPage(Long productId) {
        Product p = Product.findById(productId);
        if(p == null) redirect("/catalog");
        return ok(productDetails.render(p));
    }


}
