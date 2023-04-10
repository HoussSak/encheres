package fr.eni.encheres.controller;

import fr.eni.encheres.common.UtilisateurConnecte;
import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.exception.InvalidEntityException;
import fr.eni.encheres.model.Adresse;
import fr.eni.encheres.service.UtilisateurService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
@SecurityRequirement(name = "bearerAuth")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final UtilisateurConnecte utilisateurConnecte;
    public UtilisateurController(UtilisateurService utilisateurService, UtilisateurConnecte utilisateurConnecte) {
        this.utilisateurService = utilisateurService;
        this.utilisateurConnecte = utilisateurConnecte;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseUtilisateurDto> signup(@RequestBody CreateUtilisateurDto utilisateurDto, BindingResult result) {
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw  new InvalidEntityException("Utilisateur not valid", ErrorCodes.UTILISATEUR_NOT_VALID,errors);
        }
        log.info("Creating new user for with email: {}", utilisateurDto.getEmail());
                Tuple2<ResponseUtilisateurDto, HttpHeaders> userSaved = utilisateurService.saveUtilisateur(utilisateurDto);
        return new ResponseEntity<>(userSaved._1, userSaved._2, HttpStatus.OK);
    }
    @GetMapping("/{idUtilisateur}")
    public ResponseEntity<ResponseUtilisateurDto> findUserById(@PathVariable("idUtilisateur") Integer id ) {
        log.info("processing finding user by id: {}", id);
        return new ResponseEntity<>(utilisateurService.findById(id),HttpStatus.OK) ;
    }
    @GetMapping("/userDetails")
    public ResponseEntity<ResponseUtilisateurDto> findUserConnected(Principal principal) {
        log.info("processing finding user connected");
        Integer actualUserId =  utilisateurConnecte.getUserConnectedId(principal);
        return new ResponseEntity<>(utilisateurService.findById(actualUserId),HttpStatus.OK) ;
    }
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseUtilisateurDto>> findAllUsers() {
        log.info("processing finding all users");
        return new ResponseEntity<>(utilisateurService.findAll(),HttpStatus.OK) ;
    }
    @GetMapping(value = "/isConnected")
    public ResponseEntity<String> getUSerConnected() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return new ResponseEntity<>(((UserDetails) principal).getUsername(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not connected", HttpStatus.FORBIDDEN);
    }
    @DeleteMapping("/delete")
    public void deleteAccount(Principal principal) {
        utilisateurService.delete(principal);
    }
    @DeleteMapping("/admin/delete/{idUtilisateur}")
    public void deleteAccountByAdmin(@PathVariable("idUtilisateur") Integer id) {
        utilisateurService.deleteAccountByAdmin(id);
    }
    @PatchMapping("/admin/update/{idUtilisateur}")
    public ResponseEntity<ResponseUtilisateurDto> EditUserProfileByAdmin(@PathVariable("idUtilisateur") Integer id,
                                                             @RequestBody CreateUtilisateurDto utilisateurDto,
                                                             BindingResult result) {
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Utilisateur not valid", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }
        log.info("Updating user with id: {}", id);
        ResponseUtilisateurDto userSaved = utilisateurService.updateUtilisateurByAdmin(utilisateurDto,id);
        return new ResponseEntity<>(userSaved, HttpStatus.OK);
    }
    @PatchMapping("/update")
    public ResponseEntity<ResponseUtilisateurDto> editUserProfile(
            Principal principal,
            @RequestParam(value = "pseudo", required = false) String pseudo,
            @RequestParam(value = "nom", required = false) String nom,
            @RequestParam(value = "prenom", required = false) String prenom,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "adresseRue", required = false) String adresseRue,
            @RequestParam(value = "adresseCodePostal", required = false) String adresseCodePostal,
            @RequestParam(value = "adresseVille", required = false) String adresseVille,
            @RequestParam(value = "motDePasse", required = false) String motDePasse
    ) {
        CreateUtilisateurDto utilisateurDto = CreateUtilisateurDto.builder()
                .pseudo(pseudo)
                .nom(nom)
                .prenom(prenom)
                .email(email)
                .telephone(telephone)
                .adresse(new Adresse(adresseRue, adresseCodePostal, adresseVille))
                .motDePasse(motDePasse)
                .build();
        System.out.println(pseudo);

        Tuple2<ResponseUtilisateurDto, HttpHeaders> userSaved = utilisateurService.updateUtilisateur(utilisateurDto, principal);
        return new ResponseEntity<>(userSaved._1, userSaved._2, HttpStatus.OK);
    }



}