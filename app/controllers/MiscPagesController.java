package controllers;

import models.Product;
import models.StaticPage;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import utils.Navigation;
import views.html.main;
import views.html.productDetails;
import views.html.staticPage;


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
        return getStaticPage(Navigation.PRICE_MANUFACTURE, Navigation.PRICE_MANUFACTURE_URL);
    }

    public static Result getPriceApplication() {
        return getStaticPage(Navigation.PRICE_APPLICATION, Navigation.PRICE_APPLICATION_URL);
    }

    public static Result getAboutPage() {
        return getStaticPage(Navigation.ABOUT, Navigation.ABOUT_URL);
    }

    public static Result getContactPage() {
        return getStaticPage(Navigation.CONTACT, Navigation.CONTACT_URL);
    }

    private static Result getStaticPage(String title, String url) {
        StaticPage page = StaticPage.findByUrl(url);
        if(page == null) throw new RuntimeException("no " + url + " page!");
        return ok(staticPage.render(page));
    }
}
