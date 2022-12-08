package dev.jtm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "document")
public class Documents {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    // Quantite restante après reservation
    private int qte_res;
    // Etat pour checker la disponibilité de livre
    private boolean state;

    @ManyToOne
    @JoinColumn(name="rayons_id", nullable=false)
    private Rayons rayons;

    @ManyToOne
    @JoinColumn(name="nature_id", nullable=false)
    private Nature nature;

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "documents")
    @JsonIgnore
    private List<Incident> incidents;

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    private List<Reservation> reservations= new ArrayList<>();

   @CreationTimestamp
    private Date dateCreate;

    @UpdateTimestamp
    private Date dateUpdate;

}
