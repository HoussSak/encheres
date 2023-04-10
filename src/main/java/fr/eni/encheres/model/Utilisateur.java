package fr.eni.encheres.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "utilisateurs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utilisateur {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "no_utilisateur")
   private Integer id;
   private String pseudo;
   private String nom;
   private String prenom;
   private String email;
   private String telephone;
   @Embedded
   private Adresse adresse;
   @Column(name = "mot_de_passe")
   private String motDePasse;
   private Integer credit;
   private boolean administrateur;
   @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
   private List<ArticleVendu> articles;
   @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
   private List<Enchere> encheres;

}
