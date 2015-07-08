package models;

import com.avaje.ebean.validation.NotNull;
import sapsan.annotation.SapsanField;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by andy on 07.07.15.
 */
@Entity
@Table(name = "static_pages")
public class StaticPage extends IDNameEntity{

    @NotNull
    @SapsanField
    public String url;
    @NotNull
    @SapsanField
    public String content;
}
