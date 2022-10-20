package dev.jtm.library.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idIncident;
    private String libelle;
    private String description;

    @ManyToOne
    @JoinColumn(name = "document_id",nullable = false)
    private Document  document;



}
