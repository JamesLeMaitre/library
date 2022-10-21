package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private String libelle;
    private String description;
    // Date de reservation
    private Date date_res;
    //Date retour
    private Date date_ret;
    // Etat de la reservation : Valid or Reject
    private boolean vr;
    // Etat de la reservation : En cours de traitement ou pas
    private boolean processing;
    // Ce boolean est pour la suppression
    private boolean sup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUsers users;
    @Temporal(TemporalType.DATE)
    private Date dateCreate;

    @Temporal(TemporalType.DATE)
    private Date dateUpdate;
    @PrePersist
    private void setDateTime() {
        dateCreate = dateUpdate = new Date();
    }

    @PreUpdate
    private void updateDateTime() {
        dateUpdate = new Date();
    }
}
