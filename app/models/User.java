package models;

import com.avaje.ebean.annotation.Encrypted;
import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Users
 */
@Entity
@Table(name = "users", schema="public")
//@Label("Users")
public class User extends Model {
    /**
     * Id
     */
    @Id
//    @Label("Id")
    public Integer id;
    /**
     * Email
     */
    @Column(unique = true)
    @Length(min = 0, max = 255)
    @Constraints.Email
//    @Label("Email")
    public String email;
    /**
     * Password
     */
    @Length(min = 0, max = 32)
//    @Encrypted(dbEncryption = false)
//    @Label("Password")
    public String password;

    /**
     * Role
     */
    @Length(min = 0, max = 16)
//    @Label("Role")
    public String role;

    public static final Model.Finder<Integer, User> find = new Model.Finder<>(Integer.class, User.class);

    @Override
    public String toString() {
        return " <" + email + ">";
    }


    /**
     * Authenticate a User.
     */
    //TODO return User
    public static User authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }


}