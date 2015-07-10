package controllers;

import models.Product;
import models.StaticPage;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Navigation;
import utils.StringUtils;
import views.html.price;
import views.html.productDetails;


/**
 * Created by andy on 03.07.15.
 */
public class MiscPagesController extends Controller {

    public static Result getProductDetailsPage(Long productId) {
        Product p = Product.findById(productId);
        if (p == null) return redirect("/catalog");
        return ok(productDetails.render(p));
    }

    public static Result getPricePage() {
        return redirect(Navigation.PRICE_MANUFACTURE_URL);

    }

    public static Result getPriceManufacture() {
        StaticPage page = StaticPage.findByUrl(Navigation.PRICE_MANUFACTURE_URL);
        if(page == null) throw new RuntimeException("no price-manufacture page!, url: ");
        return ok(price.render(page));

    }

    public static Result getPriceApplication() {
        StaticPage page = StaticPage.findByUrl(StringUtils.getLastSubUrl(Navigation.PRICE_APPLICATION_URL));
        return ok(price.render(page));
    }


}
