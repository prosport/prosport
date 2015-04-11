package models;


import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by andy on 4/11/15.
 */

@Entity
@Table(name = "product")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "product_id_seq")
public class Product extends AbstractBaseEntity{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
