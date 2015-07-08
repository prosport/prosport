package controllers;

import models.StaticPage;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.staticBuilder;

/**
 * Created by andy on 06.07.15.
 */
public class StaticBuilderController extends Controller {

    public static Result getPage() {
        StaticPage page = new StaticPage();
        page.url = "/test";
        page.name = "Тест";
        page.content = "Содержимое страницы";

        return ok(staticBuilder.render(page));
    }

    public static Result putPage(Form form) {

        return ok("ok!");
    }


}
