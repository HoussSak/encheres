package fr.eni.encheres.mapper;

import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.model.UserHistory;
import fr.eni.encheres.model.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class UtilisateurMapper {
    public static Utilisateur createUtilisateurDtoToUtilisateur(CreateUtilisateurDto utilisateurDto) {
        return Utilisateur.builder()
                .nom(StringUtils.capitalize(utilisateurDto.getNom()))
                .prenom(StringUtils.capitalize(utilisateurDto.getPrenom()))
                .pseudo(StringUtils.capitalize(utilisateurDto.getPseudo()))
                .email(utilisateurDto.getEmail())
                .adresse(utilisateurDto.getAdresse())
                .telephone(utilisateurDto.getTelephone())
                .motDePasse(new BCryptPasswordEncoder().encode(utilisateurDto.getMotDePasse()))
                .credit(100)
                .administrateur(utilisateurDto.isAdministrateur())
                .build();
    }
    public static CreateUtilisateurDto utilisateurToCreateUtilisateurDto(Utilisateur utilisateur) {
        return CreateUtilisateurDto.builder()
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
                .prenom(utilisateur.getPrenom())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .credit(utilisateur.getCredit())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .administrateur(utilisateur.isAdministrateur())
                .build();
    }

  //  private static List<ResponseArticleVenduDto> getArticles(Utilisateur utilisateur) {
  //      if (utilisateur.getArticles() == null) {
  //          return List.of();
  //      }
  //     return utilisateur.getArticles().stream().map(ArticleVenduMapper::articleVenduToArticleVenduDto
  //              ).collect(Collectors.toList());
  //  }
    public static ResponseUtilisateurDto createUtilisateurToUtilisateurDtoResponse(CreateUtilisateurDto utilisateur) {
        return ResponseUtilisateurDto.builder()
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .administrateur(utilisateur.isAdministrateur())
                .build();
    }
    public static Utilisateur updateProfile(CreateUtilisateurDto utilisateurDto, Utilisateur foundUser) {
        Map<Consumer<String>, String> setters = new HashMap<>();
        setters.put(foundUser::setNom, StringUtils.capitalize(utilisateurDto.getNom()));
        setters.put(foundUser::setPrenom, StringUtils.capitalize(utilisateurDto.getPrenom()));
        setters.put(foundUser::setPseudo, StringUtils.capitalize(utilisateurDto.getPseudo()));
        setters.put(foundUser::setEmail, utilisateurDto.getEmail());
        setters.put(foundUser::setTelephone, utilisateurDto.getTelephone());

        // Mise à jour de l'adresse
        if (utilisateurDto.getAdresse() != null) {
            Adresse adresse = foundUser.getAdresse();
            if (adresse == null) {
                adresse = new Adresse();
            }
            if (utilisateurDto.getAdresse().getRue() != null) {
                adresse.setRue(utilisateurDto.getAdresse().getRue());
            }
            if (utilisateurDto.getAdresse().getCodePostal() != null) {
                adresse.setCodePostal(utilisateurDto.getAdresse().getCodePostal());
            }
            if (utilisateurDto.getAdresse().getVille() != null) {
                adresse.setVille(utilisateurDto.getAdresse().getVille());
            }
            foundUser.setAdresse(adresse);
        }
        if (utilisateurDto.getMotDePasse() != null) {
            foundUser.setMotDePasse(new BCryptPasswordEncoder().encode(utilisateurDto.getMotDePasse()));
        }

        setters.forEach((setter, value) -> {
            if (value != null) {
                setter.accept(value);
            }
        });

        return foundUser;
    }

    public static  UserHistory utilisateurToUserHistory(Utilisateur utilisateur) {
        return UserHistory.builder()
                .nom(StringUtils.capitalize(utilisateur.getNom()))
                .prenom(StringUtils.capitalize(utilisateur.getPrenom()))
                .pseudo(StringUtils.capitalize(utilisateur.getPseudo()))
                .email(utilisateur.getEmail())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .credit(utilisateur.getCredit())
                .administrateur(utilisateur.isAdministrateur())
                .build();
    }


}
