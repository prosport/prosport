package models;


import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import sapsan.annotation.SapsanField;
import sapsan.common.HtmlInputComponent;

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
    @Column(name = "category_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @SapsanField()
    public ProductCategory category;

    @NotNull
    @Constraints.Required
    @Formats.NonEmpty
    @Column(name = "semantic_url")
    @SapsanField()
    public String semanticUrl;

    @NotNull
    @Constraints.Required
    @Constraints.MaxLength(20)
    @Formats.NonEmpty
    @Column(name = "articul")
    @SapsanField()
    public String articul;

    @NotNull
    @Constraints.Required
    @Column(name = "description")
    @SapsanField(inputComponent = HtmlInputComponent.CKEditor)
    public String description;

    @NotNull
    @Constraints.Required
    @Formats.NonEmpty
    @Column(name = "short_description")
    @SapsanField(inputComponent = HtmlInputComponent.TextArea)
    public String shortDescription;

    @NotNull
    @Column(name = "rating")
    @SapsanField()
    public Double rating = 0D;

    @NotNull
    @Column(name = "views_count")
    @SapsanField()
    public Integer viewsCount = 0;

    @Column(name = "author")
    @SapsanField()
    public String author;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Image> images;

    public static Model.Finder<String, Product> find = new Model.Finder<>(String.class, Product.class);

    public static List<Product> getProductsWithCategory(String categoryName) {
        return find.where().eq("category.name", categoryName).findList();
    }

}
