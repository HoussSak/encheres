package fr.eni.encheres.mapper;

import fr.eni.encheres.dto.AdresseDto;
import fr.eni.encheres.dto.create.CreateArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.model.ArticleVendu;
import org.springframework.stereotype.Component;

@Component
public class ArticleVenduMapper {
    public static ArticleVendu articleVenduDtoToArticleVendu(CreateArticleVenduDto articleVenduDto){
        return ArticleVendu.builder()
                .nomArticle(articleVenduDto.getNomArticle())
                .description(articleVenduDto.getDescription())
                .prixInitial(articleVenduDto.getPrixInitial())
                .dateDebutEncheres(articleVenduDto.getDateDebutEncheres())
                .dateFinEncheres(articleVenduDto.getDateFinEncheres())
                .build();
    }
    public static ResponseArticleVenduDto articleVenduToArticleVenduDto(ArticleVendu articleVendu){
        return ResponseArticleVenduDto.builder()
                .id(articleVendu.getNoArticle())
                .nomArticle(articleVendu.getNomArticle())
                .description(articleVendu.getDescription())
                .prixInitial(articleVendu.getPrixInitial())
                .dateDebutEncheres(articleVendu.getDateDebutEncheres())
                .dateFinEncheres(articleVendu.getDateFinEncheres())
                .retrait(AdresseDto.builder()
                        .rue(articleVendu.getRetrait().getAdresse().getRue())
                        .codePostal(articleVendu.getRetrait().getAdresse().getCodePostal())
                        .ville(articleVendu.getRetrait().getAdresse().getVille())
                        .build())
                .articleCategorie("Velo")
                .vendeur(UtilisateurMapper.UtilisateurToUtilisateurDtoResponse(articleVendu.getUtilisateur()))
                .build();
    }
}
