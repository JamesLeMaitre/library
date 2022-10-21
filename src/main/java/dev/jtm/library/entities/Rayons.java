package dev.jtm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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

    @JsonIgnore
    @OneToMany(mappedBy="rayons")
    private List<Document> documents;

    @JsonIgnore
    @OneToMany(mappedBy="rayons")
    private List<Archives> archives;

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

/*    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;*/
}
