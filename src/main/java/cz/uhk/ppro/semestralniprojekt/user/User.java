package cz.uhk.ppro.semestralniprojekt.user;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user")
    @SequenceGenerator(name = "id_user", sequenceName = "SEQ_USER")
    private Integer id;

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Transient
    private String matchingPassword;

    @Column(name = "initial_deposit")
    @NotEmpty
    private float initialDeposit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public float getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(float initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

}