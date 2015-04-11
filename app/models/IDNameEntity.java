package models;

import utils.StringUtils;

import javax.persistence.Column;

/**
 * Created by andy on 4/11/15.
 */
public abstract class IDNameEntity extends AbstractBaseEntity{
    @Column(unique = true)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return StringUtils.getAngleBracketString(name);
    }
}
