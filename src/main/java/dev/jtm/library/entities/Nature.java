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
public class Nature {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNature;
    private  String libelle;
    private String description;

    @OneToOne(mappedBy = "nature")
    private Document document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUsers users;
}
