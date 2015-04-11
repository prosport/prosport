package models;


import org.hibernate.validator.constraints.Length;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by andy on 4/11/15.
 */

@Entity
@Table(name = "products")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "products_id_seq")
public class Product extends IDNameTimeEntity {


    @NotNull
    @Constraints.Required
    @Constraints.MaxLength(20)
    @Formats.NonEmpty
    @Column(name="articul")
    public String articul;

    @NotNull
    @Constraints.Required
    @Column(name="description")
    public String description;

    @NotNull
    @Constraints.Required
    @Formats.NonEmpty
    @Column(name="short_description")
    public String shortDescription;

    @NotNull
    @Column(name="rating")
    public Double rating = 0D;

    @NotNull
    @Column(name="views_count")
    public Integer viewsCount = 0;

    @Column(name="author")
    public String author;


    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    public List<Image> images;

}
