package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultation;
    private String libelle;
    private String description;
    private String motif;
    private Date dateConsul;
    private String observation;

    @ManyToOne
    @JoinColumn(name = "document_id",nullable = false)
    private Document  document;
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
