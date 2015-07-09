package models;

import play.db.ebean.Model;
import sapsan.annotation.SapsanField;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andy on 4/11/15.
 */
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractBaseEntity extends Model {


    @Id
    @Column(name = "id")
//    @GeneratedValue(generator = "entity_id_gen", strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    @SapsanField()
    public Long id;

    public Long getId() {
        return id;
    }


    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : (getId().hashCode() * 37);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AbstractBaseEntity) || ((AbstractBaseEntity) obj).getId() == null) {
            return false;
        }
        return getId().equals(((AbstractBaseEntity) obj).getId());
    }


}
