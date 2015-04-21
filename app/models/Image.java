package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import sapsan.annotation.SapsanField;
import sapsan.common.HtmlInputComponent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Images
 */
@Entity
@Table(name = "images" /*, schema="public" */)
@SequenceGenerator(name = "entity_id_gen", sequenceName = "images_id_seq")
public class Image extends IDNameEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @SapsanField
    public Product product;

    @NotNull
    @SapsanField
    public String color;

    @NotNull
    @SapsanField(inputComponent = HtmlInputComponent.FileUpload)
    public String filename;

    public static Model.Finder<Long, Image> find = new Model.Finder<>(Long.class, Image.class);
}