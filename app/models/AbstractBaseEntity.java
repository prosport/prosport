package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andy on 4/11/15.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractBaseEntity implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "entity_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : (getId().hashCode() * 37);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AbstractBaseEntity) || ((AbstractBaseEntity) obj).getId() == null) {
            return false;
        }
        return getId().equals(((AbstractBaseEntity) obj).getId());
    }
}
