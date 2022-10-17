package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "document_id",nullable = false)
    private Document  document;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;
}
