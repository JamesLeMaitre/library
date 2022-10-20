package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rayons {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRayons;
    private String libelle;
    private String acronym;

    @OneToMany(mappedBy="rayons")
    private List<Document> documents;

/*    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;*/
}
