package dev.jtm.library.entities;

import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

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
    @JoinColumn(name="rayons_id", nullable=false)
    private Rayons rayons;


    @CreationTimestamp
    private Date dateCreate;

    @UpdateTimestamp
    private Date dateUpdate;

}
