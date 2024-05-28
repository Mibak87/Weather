package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session {
    @Id
    @Column(name = "ID")
    private String id;

    @OneToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "ExpiresAt")
    private Date expiresAt;
}
