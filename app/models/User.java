package models;

import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import sapsan.annotation.SapsanField;
import utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Users
 */
@Entity
@Table(name = "users")
@SequenceGenerator(name = "entity_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
public class User extends AbstractBaseEntity {

    @NotNull
    @Column(unique = true)
    @Length(min = 0, max = 255)
    @Constraints.Email
    @SapsanField()
    public String email;

    @SapsanField()
    @Length(min = 0, max = 32)
    public String password;

    @SapsanField()
    @Length(min = 0, max = 16)
    public SecurityRole role;

    @SapsanField()
    @Column(name = "registed_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    public Date registredAt;

    @SapsanField()
    @Column(name = "is_blocked")
    public Boolean isBlocked;

    @Override
    public String toString() {
        return StringUtils.getAngleBracketString(email);
    }

    public static Model.Finder<Long,User> find = new Model.Finder<>(Long.class, User.class);

    public static User authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}