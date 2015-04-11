package models;

import org.hibernate.validator.constraints.Length;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import utils.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

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
    private String email;

    @Length(min = 0, max = 32)
    private String password;

    @Length(min = 0, max = 16)
    public String role;

    @Column(name = "registed_at")
    public Date registredAt;

    @Column(name = "is_blocked")
    public Boolean isBlocked;

    @Override
    public String toString() {
        return StringUtils.getAngleBracketString(email);
    }

    public static User authenticate(String email, String password) {
        try {
            return JPA.em().createQuery("FROM User WHERE email = :email AND password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch(NoResultException ex){
            return null;
        } catch(NonUniqueResultException ex) {
            throw ex;
        }
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}