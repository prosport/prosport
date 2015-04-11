package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Images
 */
@Entity
@Table(name = "images" /*, schema="public" */)
@SequenceGenerator(name = "entity_id_gen", sequenceName = "images_id_seq")
public class Image extends IDNameEntity {

    @Constraints.MaxLength(255)
    @NotNull
    public String filename;

    @Constraints.MaxLength(255)
    @NotNull
    public String color;


    @ManyToOne(cascade = CascadeType.ALL)
    public Product product;


}