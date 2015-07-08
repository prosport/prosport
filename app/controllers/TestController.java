package controllers;

import com.avaje.ebean.Ebean;
import models.Product;
import models.ProductCategory;
import play.mvc.Result;

import java.util.Date;

import static play.mvc.Results.ok;

/**
 * Created by rumata on 7/6/15.
 */
public class TestController {

    public static Result test02() {
        final Long MAGIC_ID = 212L;
//        ProductCategory category = ProductCategory.find.byId(MAGIC_ID);
        ProductCategory category = Ebean.find(ProductCategory.class).where().eq("id", MAGIC_ID).findUnique();
        category.name = "test-" + System.currentTimeMillis();
//        Ebean.update(category);
        return ok(category.name);
    }

    public static Result test01() {
        Product product = new Product();
        product.author = "zxzxc";
        product.name = "zxczxc";
        product.viewsCount = 5;
        product.description = "sdfasdgfdgdfhd";
        product.articul = "111";
        product.articul = "111";
        product.category = ProductCategory.find.byId(10L);
        product.semanticUrl = "1sdfdfsd";
        product.shortDescription = "1sdfdfsd";
//        product.createdAt = new Date();
        Ebean.save(product);
        return ok(product.name);
    }


}
