package dev.jtm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Nature {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNature;
    private  String libelle;
    private String description;

    @OneToMany(mappedBy = "nature")
    @JsonIgnore
    private List<Document> documentList;
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

   /* @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUsers users;*/
}
