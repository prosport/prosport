package models;

import javax.persistence.Column;
import java.sql.Date;

/**
 * Created by rumata on 4/11/15.
 */
public class IDNameTimeEntity extends IDNameEntity {
    @Column()
    protected Date createdAt;

    @Column()
    protected Date modifiedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
