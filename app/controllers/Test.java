package controllers;

import play.mvc.Controller;
import play.mvc.Result;


/**
 * Created by rumata on 4/12/15.
 */
public class Test extends Controller {


    //
//    @Transactional(readOnly = true)
    public static Result test1() {
////        try {
//            ProductCategory c = JPA.em()
//                    .createQuery("FROM ProductCategory WHERE name = :name", ProductCategory.class)
//                    .setParameter("name", "Бокс")
//                    .getSingleResult();
////        } catch(NoResultException ex){
////            return null;
////        } catch(NonUniqueResultException ex) {
////            throw ex;
////        }
        return ok("");
    }
}
