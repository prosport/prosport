package models;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by andy on 4/11/15.
 */
@Entity
@Table(name = "product_categories")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "productcategories_id_seq")
public class ProductCategory extends IDNameTimeEntity {



}
