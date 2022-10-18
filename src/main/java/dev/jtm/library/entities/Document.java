package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocument;
    private String libelle;
    private String description;
    private String refer;
    private String isbn;
    private String annee_edition;
    private String nom;
    private String prenom;
    private String theme_titre;
    private int quantite;
    private int nbPages;

    @ManyToOne
    @JoinColumn(name="rayons_id", nullable=false)
    private Rayons rayons;

    @ManyToOne
    @JoinColumn(name="nature_id", nullable=false)
    private Nature nature;

    @OneToMany(mappedBy = "document")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "document")
    private List<Incident> incidents;

    @OneToMany(mappedBy = "document")
    private Collection<Reservation> reservations= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;





}
