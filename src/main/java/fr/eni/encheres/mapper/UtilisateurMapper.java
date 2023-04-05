package fr.eni.encheres.mapper;

import fr.eni.encheres.dto.UtilisateurDto;
import fr.eni.encheres.model.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UtilisateurMapper {
    public Utilisateur UtilisateurDtoToUtilisateur(UtilisateurDto utilisateurDto) {
        return Utilisateur.builder()
                .nom(StringUtils.capitalize(utilisateurDto.getNom()))
                .pseudo(StringUtils.capitalize(utilisateurDto.getPseudo()))
                .email(utilisateurDto.getEmail())
                .adresse(utilisateurDto.getAdresse())
                .telephone(utilisateurDto.getTelephone())
                .motDePasse(new BCryptPasswordEncoder().encode(utilisateurDto.getMotDePasse()))
                .credit(utilisateurDto.getCredit())
                .administrateur(utilisateurDto.isAdministrateur())
                .achete(utilisateurDto.getAchete())
                .vend(utilisateurDto.getVend())
                .build();
    }
    public static UtilisateurDto UtilisateurToUtilisateurDto(Utilisateur utilisateur) {
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .pseudo(utilisateur.getPseudo())
                .email(utilisateur.getEmail())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .credit(utilisateur.getCredit())
                .administrateur(utilisateur.isAdministrateur())
                .achete(utilisateur.getAchete())
                .vend(utilisateur.getVend())
                .build();
    }
}
