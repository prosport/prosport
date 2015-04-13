package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andy on 4/11/15.
 */
@Entity
@Table(name = "product_categories")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "productcategories_id_seq")
public class ProductCategory extends IDNameTimeEntity {

    @ManyToOne
    private ProductCategory parent;

    @NotNull
    @OneToMany(mappedBy = "parent")
    private Set<ProductCategory> subCategories = new HashSet<>();

    public ProductCategory() {
    }

    public ProductCategory(final ProductCategory parent) {
        if(parent == null) throw new IllegalArgumentException("Parent required!");
        this.parent = parent;
        registerInParentsChilds();
    }

    private void registerInParentsChilds() {
        this.parent.subCategories.add(this);
    }

    public void move(final ProductCategory newParent)  {
        if(parent == null) throw new IllegalArgumentException("Parent required!");


        this.parent.subCategories.remove(this);
        this.parent = newParent;
        registerInParentsChilds();
    }

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    public Set<ProductCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<ProductCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
