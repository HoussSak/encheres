package fr.eni.encheres.controller;


import fr.eni.encheres.jwt.JwtFilter;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.service.UtilisateurService;
import io.vavr.Tuple2;
import jakarta.validation.Valid;
import fr.eni.encheres.jwt.JwtController;
import fr.eni.encheres.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtController jwtController;



    @PostMapping("/users")
    public ResponseEntity<Utilisateur> add(@Valid @RequestBody Utilisateur utilisateur) {
        Utilisateur savedUser = utilisateurService.saveUtilisateur(utilisateur);
        Authentication authentication = jwtController.logUser(utilisateur.getEmail(), utilisateur.getMotDePasse());
        String jwt = jwtUtils.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(savedUser, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/isConnected")
    public ResponseEntity getUSerConnected() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return new ResponseEntity(((UserDetails) principal).getUsername(), HttpStatus.OK);
        }
        return new ResponseEntity("User is not connected", HttpStatus.FORBIDDEN);
    }

}