package fr.eni.encheres.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "userhistories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserHistory {
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
   private Integer credit;
   private boolean administrateur;

}
