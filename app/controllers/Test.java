package controllers;

import models.ProductCategory;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import static play.data.Form.form;


/**
 * Created by rumata on 4/12/15.
 */
public class Test extends Controller {



    @Transactional(readOnly = true)
    public static Result test1() {
//        try {
            ProductCategory c = JPA.em()
                    .createQuery("FROM ProductCategory WHERE name = :name", ProductCategory.class)
                    .setParameter("name", "Бокс")
                    .getSingleResult();
//        } catch(NoResultException ex){
//            return null;
//        } catch(NonUniqueResultException ex) {
//            throw ex;
//        }
        return ok( c.getName());
    }
}
