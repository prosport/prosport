package models;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by andy on 4/11/15.
 */
@Entity
@Table(name = "productCategories")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "productCategories_id_seq")
public class ProductCategory extends IDNameEntity {

}
