package models;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.avaje.ebean.validation.NotNull;
import play.db.ebean.Model;
import sapsan.annotation.SapsanField;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by andy on 07.07.15.
 */
@Entity
@Table(name = "pages")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "pages_seq")
@EntityConcurrencyMode(ConcurrencyMode.NONE)
public class StaticPage extends IDNameEntity{

    public static Model.Finder<Long, StaticPage> find = new Model.Finder<>(Long.class, StaticPage.class);

    @SapsanField
    public String url;
    @NotNull
    @SapsanField
    public String content;
}
