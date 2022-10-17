package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;


}
