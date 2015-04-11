package dao;

import models.AbstractBaseEntity;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by andy on 4/12/15.
 */
public class DAOHelper {

    public static <T extends AbstractBaseEntity> T get(Class<T> persistentClass, Long id) {
        return id != null ? JPA.em().find(persistentClass, id) : null;
    }

    public static <T extends AbstractBaseEntity> List<T> getAll(Class<T> persistentClass) {
        return JPA.em().createQuery("FROM " + persistentClass.getSimpleName(), persistentClass).getResultList();
    }

    public static  <T extends AbstractBaseEntity> void update(T entity) {
        if (entity != null && entity.getId() != null) {
            JPA.em().merge(entity);
            flush();
        }
    }

    public static <T extends AbstractBaseEntity> void remove(Class<T> persistentClass, T entity) {
        if (entity != null && entity.getId() != null) {
            T pEntity = (T) JPA.em().find(persistentClass, entity.getId());
            JPA.em().remove(pEntity);
            flush();
        }
    }

    public static void flush() {
        JPA.em().flush();
    }

    public static <T extends AbstractBaseEntity> List<T> findByAttribute(Class<T> persistentClass, String attr, String value) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("FROM " + persistentClass.getSimpleName())
                .append(" c WHERE c.")
                .append(attr)
                .append(" = '")
                .append(value)
                .append("'");
        Query q = JPA.em().createQuery(sb.toString());
        return q.getResultList();

    }



}