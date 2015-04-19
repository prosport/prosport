package models;

import sapsan.annotation.SapsanField;
import utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Created by andy on 4/11/15.
 */

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class IDNameEntity extends AbstractBaseEntity{
    @SapsanField()
    @Column(unique = true)
    public String name;

    @Override
    public String toString() {
        return StringUtils.getAngleBracketString(name);
    }
}
