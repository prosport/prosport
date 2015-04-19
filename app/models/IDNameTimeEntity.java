package models;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.util.Date;


/**
 * Created by rumata on 4/11/15.
 */
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class IDNameTimeEntity extends IDNameEntity {
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    public Date createdAt;

    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    public Date modifiedAt = new Date();

}
