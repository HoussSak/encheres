package fr.eni.encheres.common;

import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
@Component
public class UtilisateurConnecte {
    private final UserRepository userRepository;

    public UtilisateurConnecte(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  Integer getUserConnectedId(Principal principal) {
        if (!(principal instanceof UsernamePasswordAuthenticationToken)) {
            throw new RuntimeException(("User not found"));
        }
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Utilisateur oneByEmail = userRepository.findOneByEmail(token.getName());
        return oneByEmail.getId();
    }
}
