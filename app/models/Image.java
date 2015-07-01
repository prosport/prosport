package models;

import play.data.validation.Constraints;
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

    @ManyToOne
    @SapsanField
    public Product product;

    @NotNull
    @SapsanField
    public String color;

    @NotNull
    @SapsanField(inputComponent = HtmlInputComponent.FileUpload)
    public String filename;

}