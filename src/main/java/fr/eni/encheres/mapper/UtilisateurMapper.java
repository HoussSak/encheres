package fr.eni.encheres.mapper;

import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.model.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UtilisateurMapper {
    public static Utilisateur createUtilisateurDtoToUtilisateur(CreateUtilisateurDto utilisateurDto) {
        return Utilisateur.builder()
                .nom(StringUtils.capitalize(utilisateurDto.getNom()))
                .pseudo(StringUtils.capitalize(utilisateurDto.getPseudo()))
                .email(utilisateurDto.getEmail())
                .adresse(utilisateurDto.getAdresse())
                .telephone(utilisateurDto.getTelephone())
                .motDePasse(new BCryptPasswordEncoder().encode(utilisateurDto.getMotDePasse()))
                .credit(utilisateurDto.getCredit())
                .administrateur(utilisateurDto.isAdministrateur())
                .articles(utilisateurDto.getArticles())
                .build();
    }
    public static CreateUtilisateurDto utilisateurToCreateUtilisateurDto(Utilisateur utilisateur) {
        return CreateUtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .credit(utilisateur.getCredit())
                .administrateur(utilisateur.isAdministrateur())
                .build();
    }

    public static ResponseUtilisateurDto utilisateurToUtilisateurDtoResponse(Utilisateur utilisateur) {
        return ResponseUtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .administrateur(utilisateur.isAdministrateur())
                .articles(getArticles(utilisateur))
                .build();
    }

    private static List<ResponseArticleVenduDto> getArticles(Utilisateur utilisateur) {
       return utilisateur.getArticles().stream().map(ArticleVenduMapper::articleVenduToArticleVenduDto
                ).collect(Collectors.toList());
    }

    public static ResponseUtilisateurDto createUtilisateurToUtilisateurDtoResponse(CreateUtilisateurDto utilisateur) {
        return ResponseUtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .administrateur(utilisateur.isAdministrateur())
                .build();
    }

    public static Utilisateur updateUpate(CreateUtilisateurDto utilisateurDto, Utilisateur foundUser) {
        return Utilisateur.builder()
                .id(foundUser.getId())
                .nom(StringUtils.capitalize(utilisateurDto.getNom()))
                .pseudo(StringUtils.capitalize(utilisateurDto.getPseudo()))
                .email(utilisateurDto.getEmail())
                .adresse(utilisateurDto.getAdresse())
                .telephone(utilisateurDto.getTelephone())
                .motDePasse(new BCryptPasswordEncoder().encode(utilisateurDto.getMotDePasse()))
                .build();
    }
}
