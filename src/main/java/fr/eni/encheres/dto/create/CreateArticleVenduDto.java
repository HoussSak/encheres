package fr.eni.encheres.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.eni.encheres.model.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class CreateArticleVenduDto {
    private Integer id;
    @NotBlank(message = "Veuillez renseigner le nom de l'article")
    private String nomArticle;
    @NotBlank(message = "Veuillez renseigner le nom de la déscription")
    private String description;
    @NotBlank(message = "Veuillez renseigner la date de début")
    private Instant dateDebutEncheres;
    @NotBlank(message = "Veuillez renseigner la date de fin")
    private Instant dateFinEncheres;
    @NotBlank(message = "Veuillez renseigner le prix initial")
    private BigDecimal prixInitial;
    private BigDecimal prixVente;
    @JsonIgnore
    private Utilisateur acheteur;
    @JsonIgnore
    private Utilisateur vendeur;
    @JsonIgnore

    private Categorie articleCategorie;
    @JsonIgnore
    private Retrait retrait;
    @JsonIgnore
    private Enchere enchere;

}
