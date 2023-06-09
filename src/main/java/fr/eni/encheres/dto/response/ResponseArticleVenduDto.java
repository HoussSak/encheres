package fr.eni.encheres.dto.response;

import fr.eni.encheres.dto.AdresseDto;
import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class ResponseArticleVenduDto {
    private Integer id;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private Integer prixInitial;
    private Integer prixVente;
    private String  articleCategorie;
    @Embedded
    private AdresseDto retrait;
    private  ResponseUtilisateurDto vendeur;
    private ResponseEnchereDto enchere;

}
