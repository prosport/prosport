package controllers;

import com.avaje.ebean.Ebean;
import models.ProductCategory;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * Created by rumata on 7/6/15.
 */
public class TestController {

    public static Result test01() {
        final Long MAGIC_ID = 212L;
//        ProductCategory category = ProductCategory.find.byId(MAGIC_ID);
        ProductCategory category = Ebean.find(ProductCategory.class).where().eq("id", MAGIC_ID).findUnique();
        category.name = "test-" + System.currentTimeMillis();
//        Ebean.update(category);
        return ok(category.name);
    }
}
