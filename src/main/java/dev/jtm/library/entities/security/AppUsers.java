package dev.jtm.library.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jtm.library.entities.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUsers {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Boolean isNotLocked = true;

    private Boolean isActive = false;

    @OneToMany(mappedBy = "users")
    private Collection<Reservation> reservations= new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Document> documents;

    @OneToMany(mappedBy = "users")
    private List<Rayons> rayons;

    @OneToMany(mappedBy = "users")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "users")
    private List<Nature> natures;

    @OneToMany(mappedBy = "users")
    private List<Archives> archives;

    @OneToMany(mappedBy = "users")
    private List<Incident> incidents;

    // Relation with role
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "idRole"))
    @ManyToMany
    @ToString.Exclude
    private Collection<AppRole> roles = new ArrayList<>();

}
