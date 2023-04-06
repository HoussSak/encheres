package fr.eni.encheres.service.helper;

import fr.eni.encheres.common.UtilisateurConnecte;
import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.exception.EntityNotFoundException;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.jwt.JwtController;
import fr.eni.encheres.jwt.JwtFilter;
import fr.eni.encheres.jwt.JwtUtils;
import fr.eni.encheres.mapper.UtilisateurMapper;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.UserRepository;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UtilisateurServiceHelper {
    private final UserRepository userRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final JwtController jwtController;
    private final UtilisateurConnecte utilisateurConnecte;

    private final JwtUtils jwtUtils;

    public UtilisateurServiceHelper(UserRepository userRepository, UtilisateurMapper utilisateurMapper, JwtController jwtController, UtilisateurConnecte utilisateurConnecte, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.jwtController = jwtController;
        this.utilisateurConnecte = utilisateurConnecte;
        this.jwtUtils = jwtUtils;
    }
    @Transactional
    public Tuple2<ResponseUtilisateurDto, HttpHeaders> saveUtlisateur(CreateUtilisateurDto utilisateurDto) {
        Utilisateur savedUser = userRepository.save(UtilisateurMapper.createUtilisateurDtoToUtilisateur(utilisateurDto));
        log.info("User is created with id: {}", savedUser.getId());
        ResponseUtilisateurDto user = UtilisateurMapper.utilisateurToUtilisateurDtoResponse(savedUser);
        Authentication authentication = jwtController.logUser(utilisateurDto.getEmail(), utilisateurDto.getMotDePasse());
        String jwt = jwtUtils.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return Tuple.of(user,httpHeaders);
    }
    public ResponseUtilisateurDto findById(Integer id) {
        Optional<Utilisateur> utilisateur = userRepository.findById(id);
        ResponseUtilisateurDto utilisateurDto = UtilisateurMapper.utilisateurToUtilisateurDtoResponse(utilisateur.get());
        return Optional.of(utilisateurDto).orElseThrow(()->
                new EntityNotFoundException("Utilisateur with ID = "+id+" not found", ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    public void delete(Principal principal) {
        Integer actualUserId =  utilisateurConnecte.getUserConnectedId(principal);
        if (principal == null) {
            log.error("Utilisateur with ID = "+principal+" not found ");
        }
        userRepository.deleteById(actualUserId);
    }

    public void deleteAccount(Integer id) {
        if (id == null) {
            log.error("Utilisateur with ID = "+id+" not found ");}
        userRepository.deleteById(id);
    }

    public List<ResponseUtilisateurDto> readAllUsers() {
        return userRepository.findAll().stream()
                .map(UtilisateurMapper::utilisateurToUtilisateurDtoResponse).
                collect(Collectors.toList());
    }
}
