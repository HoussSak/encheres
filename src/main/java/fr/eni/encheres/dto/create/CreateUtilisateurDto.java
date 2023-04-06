package fr.eni.encheres.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateUtilisateurDto {
    private Integer id;
    @NotBlank(message = "Veuillez renseigner le pseudo")
    private String pseudo;
    @NotBlank(message = "Veuillez renseigner le nom")
    private String nom;
    @NotBlank(message = "Veuillez renseigner le prenom")
    private String prenom;
    @NotBlank(message = "Veuillez renseigner l''adresse mail")
    @Email(message = "Veuillez saisir une adresse electronique correcte")
    private String email;
    @Size(min = 10,max = 14,message = "Veillez renseigner un umero de telephone valide !")
    private String telephone;
    @Embedded
    private Adresse adresse;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre, un caractère spécial et avoir une longueur minimale de 8 caractères.")
    private String motDePasse;
    private int credit;
    private boolean administrateur;
    @JsonIgnore
    private List<ArticleVendu> articles;
    @JsonIgnore
    private List<Enchere> encheres;

}
