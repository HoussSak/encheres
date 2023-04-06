package fr.eni.encheres.controller;


import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.exception.InvalidEntityException;
import fr.eni.encheres.service.UtilisateurService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
@SecurityRequirement(name = "bearerAuth")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
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
    @GetMapping("/all")
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
    @DeleteMapping("/delete/{idUtilisateur}")
    public void deleteAccountByAdmin(@PathVariable("idUtilisateur") Integer id) {
        utilisateurService.deleteAccountByAdmin(id);
    }


}