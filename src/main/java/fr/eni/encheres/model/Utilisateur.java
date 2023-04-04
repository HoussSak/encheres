package fr.eni.encheres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "utilisateurs")
public class Utilisateur extends AbstractEntity{
   private String pseudo;
   private String nom;
   private String email;
   private String telephone;
   @Embedded
   private Adresse adresse;
   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre, un caractère spécial et avoir une longueur minimale de 8 caractères.")
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
