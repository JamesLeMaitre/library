package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    private Date dateCreate;

    @UpdateTimestamp
    private Date dateUpdate;

}
