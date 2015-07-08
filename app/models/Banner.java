package models;


import play.db.ebean.Model;
import sapsan.annotation.SapsanField;

import javax.persistence.*;

/**
 * Created by rumata on 01.07.15.
 */
@Entity
@Table(name = "banners")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "banners_seq")
public class Banner extends IDNameTimeEntity {

    @SapsanField()
    public String url;

    @SapsanField()
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Image image;

    public static Model.Finder<Long, Banner> find = new Model.Finder<>(Long.class, Banner.class);

}
