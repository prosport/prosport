package models;

import play.db.ebean.Model;
import sapsan.annotation.SapsanField;
import utils.Navigation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andy on 4/11/15.
 */
@Entity
@Table(name = "product_categories")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "productcategories_id_seq")
public class ProductCategory extends IDNameTimeEntity implements Comparable<ProductCategory> {


    @SapsanField
    public Integer sortOrder;

    @SapsanField
    public String url;

    @ManyToOne
    @SapsanField()
    public ProductCategory parent;

    @NotNull
    @OneToMany(mappedBy = "parent")
    public Set<ProductCategory> subCategories = new HashSet<>();

    public static Model.Finder<Long, ProductCategory> find = new Model.Finder<>(Long.class, ProductCategory.class);

    public static List<ProductCategory> getRootCategories() {
        return find.fetch("subCategories").where()
                .eq("parent", null)
                .findList();
    }

    public static ProductCategory getLeftmostRoot() {
        return find.fetch("subCategories").where()
                .eq("parent", null)
                .orderBy("sortOrder")
                .setMaxRows(1)
                .findUnique();
    }

    public static ProductCategory findByUrl(String url) {
        return find.where()
                .eq("url", url)
                .findUnique();
    }

    public ProductCategory() {
    }

    public ProductCategory(final ProductCategory parent) {
        if (parent == null) throw new IllegalArgumentException("Parent required!");
        this.parent = parent;
        registerInParentsChilds();
    }

    private void registerInParentsChilds() {
        this.parent.subCategories.add(this);
    }

    public void move(final ProductCategory newParent) {
        if (parent == null) throw new IllegalArgumentException("Parent required!");


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

    /**
     * Вернуть самого глубокого левого потомка, или себя, если таких нет
     *
     * @return
     */
    public ProductCategory getLeftDeepestChildrenOrSelf() {
        if (subCategories.isEmpty()) return this;
        return subCategories.stream().sorted().findFirst().get().getLeftDeepestChildrenOrSelf();
    }

    public String getFullUrl() {
        if (parent == null) return Navigation.CATALOG_URL + '/' + url;
        else return parent.getFullUrl() + "/" + url;
    }

    @Override
    public int compareTo(ProductCategory o) {
        if (o == null) return 1;
        if (o.sortOrder == null) return 1;
        if (sortOrder == null) return -1;
        return sortOrder.compareTo(o.sortOrder);
    }
}
