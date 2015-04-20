package controllers;

import com.avaje.ebean.Ebean;
import models.SecurityRole;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;


/**
 * Created by rumata on 4/12/15.
 */
public class Test extends Controller {

    public static Result test1() {
        User u = User.find.where().eq("email", "valera.dt@gmail.com").findUnique();
        return ok(u.role.name());
    }

    public static Result test3() {
//        User u = new User();
//        u.email = "valera@lastochka-os.ru";
//        u.password = "123456";
//        u.role = SecurityRole.ROLE_ADMIN;
//        u.registredAt = new Date();
//        Ebean.save(u);

        return ok("!!!");
    }

}
