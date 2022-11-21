package dev.jtm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    private List<Incident> incidents;

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    private Collection<Reservation> reservations= new ArrayList<>();
    @Temporal(TemporalType.DATE)
    private Date dateCreate;

    @UpdateTimestamp
    private Date dateUpdate;

}
