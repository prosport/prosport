package controllers;

import models.Product;
import models.StaticPage;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.price;
import views.html.productDetails;


/**
 * Created by andy on 03.07.15.
 */
public class MiscPagesController extends Controller {

    public static Result getProductDetailsPage(Long productId) {
        Product p = Product.findById(productId);
        if(p == null) redirect("/catalog");
        return ok(productDetails.render(p));
    }

    public static Result getPricePage() {
        StaticPage page = StaticPage.findByUrl("/price");
        return ok(price.render(page));
    }


}
