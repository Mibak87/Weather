package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Sessions")
public class Session {
    @Id
    @Column(name = "ID")
    private String id;

    @OneToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "ExpiresAt")
    private Date expiresAt;

    public Session() {
    }

    public Session(String id, User user, Date expiresAt) {
        this.id = id;
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
