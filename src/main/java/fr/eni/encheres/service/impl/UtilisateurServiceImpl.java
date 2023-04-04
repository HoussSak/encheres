package fr.eni.encheres.service.impl;

import fr.eni.encheres.jwt.JwtController;
import fr.eni.encheres.jwt.JwtFilter;
import fr.eni.encheres.jwt.JwtUtils;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.UserRepository;
import fr.eni.encheres.service.UtilisateurService;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UserRepository userRepository;
    private final JwtController jwtController;
    private final JwtUtils jwtUtils;

    public UtilisateurServiceImpl(UserRepository userRepository, JwtController jwtController, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtController = jwtController;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        Utilisateur existingUser = userRepository.findOneByEmail(utilisateur.getEmail());
        if(existingUser != null) {
            return null;
        }

        Utilisateur user = new Utilisateur();
        user.setEmail(utilisateur.getEmail());
        user.setMotDePasse(new BCryptPasswordEncoder().encode(utilisateur.getMotDePasse()));
        user.setNom(StringUtils.capitalize(utilisateur.getNom()));
        user.setPseudo(StringUtils.capitalize(utilisateur.getPseudo()));
        Utilisateur userSaved = userRepository.save(user);
        return userSaved;
    }
}
