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

    @Column(name = "initial_deposit")
    @NotEmpty
    private float initial_deposit;

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

    public float getInitial_deposit() {
        return initial_deposit;
    }

    public void setInitial_deposit(float initial_deposit) {
        this.initial_deposit = initial_deposit;
    }

}