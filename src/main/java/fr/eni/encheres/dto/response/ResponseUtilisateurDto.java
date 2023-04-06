package fr.eni.encheres.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseUtilisateurDto {
    private Integer id;
    private String pseudo;
    private String nom;
    private String email;
    private String telephone;
    @Embedded
    private Adresse adresse;
    private boolean administrateur;
    private List<ResponseArticleVenduDto> articles;
    @JsonIgnore
    private List<Enchere> encheres;

}
