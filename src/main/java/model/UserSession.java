package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Sessions")
public class UserSession {
    @Id
    @Column(name = "ID")
    private String id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "ExpiresAt")
    private Date expiresAt;

    public UserSession() {
    }

    public UserSession(String id, User user, Date expiresAt) {
        this.id = id;
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
