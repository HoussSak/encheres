package fr.eni.encheres.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UtilisateurDto {
    private Integer id;
    @NotBlank(message = "Veuillez renseigner le pseudo")
    private String pseudo;
    @NotBlank(message = "Veuillez renseigner le nom")
    private String nom;
    @NotBlank(message = "Veuillez renseigner l''adresse mail")
    @Email(message = "Veuillez saisir une adresse \\u00e9lectronique correcte")
    private String email;
    @Size(min = 10,max = 14,message = "Veillez renseigner un umero de telephone valide !")
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
