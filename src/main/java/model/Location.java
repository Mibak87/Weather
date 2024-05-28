package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Name")
    private String name;

    @OneToMany
    @JoinColumn(name = "UserID")
    private List<User> users;

    @Column(name = "Latitude")
    private long latitude;

    @Column(name = "Longitude")
    private long longitude;
}
