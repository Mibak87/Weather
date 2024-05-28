package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Login")
    private String login;

    @Column(name = "Password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
