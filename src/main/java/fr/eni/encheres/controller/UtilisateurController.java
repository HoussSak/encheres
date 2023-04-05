package fr.eni.encheres.controller;


import fr.eni.encheres.dto.UtilisateurDto;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.exception.InvalidEntityException;
import fr.eni.encheres.service.UtilisateurService;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UtilisateurDto> signup(@RequestBody UtilisateurDto utilisateurDto, BindingResult result) {
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw  new InvalidEntityException("Utilisateur not valid", ErrorCodes.UTILISATEUR_NOT_VALID,errors);
        }
        log.info("Creating new user for with email: {}", utilisateurDto.getEmail());
                Tuple2<UtilisateurDto, HttpHeaders> userSaved = utilisateurService.saveUtilisateur(utilisateurDto);
        return new ResponseEntity<>(userSaved._1, userSaved._2, HttpStatus.OK);
    }
    @GetMapping(value = "/isConnected")
    public ResponseEntity<String> getUSerConnected() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return new ResponseEntity<>(((UserDetails) principal).getUsername(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not connected", HttpStatus.FORBIDDEN);
    }

}