package dev.jtm.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jtm.library.entities.security.AppUsers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Long id;
    private String libelle;
    private String acronym;

    private Boolean used;

    @JsonIgnore
    @OneToMany(mappedBy="rayons")
    private List<Documents> documents;

    @JsonIgnore
    @OneToMany(mappedBy="rayons")
    private List<Archives> archives;

  @CreationTimestamp
    private Date dateCreate;

    @UpdateTimestamp
    private Date dateUpdate;

/*    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUsers users;*/
}
