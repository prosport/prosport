package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;


/**
 * Created by rumata on 4/12/15.
 */
public class Test extends Controller {

    public static Result test1() {
        User u = User.find.where().eq("email", "valera.dt@gmail.com").findUnique();
        return ok(u.role.name());
    }

    public static Result test3(Long id) {

        return ok("!!!" + id );
    }

}
