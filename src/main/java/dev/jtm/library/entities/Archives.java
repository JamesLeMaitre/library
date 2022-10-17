package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Archives {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArchives;
    private String libelle;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;
}
