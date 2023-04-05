package fr.eni.encheres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "utilisateurs")
public class Utilisateur extends AbstractEntity{
   private String pseudo;
   private String nom;
   private String email;
   private String telephone;
   @Embedded
   private Adresse adresse;
   private String motDePasse;
   private int credit;
   private boolean administrateur;
   @OneToMany(mappedBy = "acheteur")
   @JsonIgnore
   private List<ArticleVendu> achete;
   @OneToMany(mappedBy = "vendeur")
   @JsonIgnore
   private List<ArticleVendu> vend;
   @OneToMany(mappedBy = "UtilisateurEnchere")
   @JsonIgnore
   private List<Enchere> encheres;

}
