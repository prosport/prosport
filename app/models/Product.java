package models;


import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by andy on 4/11/15.
 */

@Entity
@Table(name = "products")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "products_id_seq")
public class Product extends IDNameTimeEntity {


}
