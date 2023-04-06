package fr.eni.encheres.service.impl;

import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.UserRepository;
import fr.eni.encheres.service.UtilisateurService;
import fr.eni.encheres.service.helper.UtilisateurServiceHelper;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UserRepository userRepository;

    private final UtilisateurServiceHelper utilisateurServiceHelper;

    public UtilisateurServiceImpl(UserRepository userRepository, UtilisateurServiceHelper utilisateurServiceHelper) {
        this.userRepository = userRepository;
        this.utilisateurServiceHelper = utilisateurServiceHelper;
    }

    @Override
    @Transactional
    public Tuple2<ResponseUtilisateurDto,HttpHeaders> saveUtilisateur(CreateUtilisateurDto utilisateurDto) {
        Utilisateur existingUser = userRepository.findOneByEmail(utilisateurDto.getEmail());
        if(existingUser != null) {
            log.error("found existing user with same email in the bdd: {}", utilisateurDto.getEmail());
            return null;
        }
        Tuple2<ResponseUtilisateurDto,HttpHeaders> response =utilisateurServiceHelper.saveUtlisateur(utilisateurDto);
        if(response == null) {
            log.error("Veuillez renseigner tous les champs correctement: {}", utilisateurDto.getEmail());
            return null;
        }
        return Tuple.of(response._1,response._2);
    }

    @Override
    public ResponseUtilisateurDto findById(Integer id) {
        if (id==null) {
            log.error("Utilisateur ID is null");
            return null;
        }
       return utilisateurServiceHelper.findById(id);
    }
    @Override
    public List<ResponseUtilisateurDto> findAll() {
        return utilisateurServiceHelper.readAllUsers();
    }
    @Override
    public void delete(Principal principal) {
        utilisateurServiceHelper.delete(principal);
    }

    @Override
    public void deleteAccountByAdmin(Integer id) {
        utilisateurServiceHelper.deleteAccount(id);
    }

    @Override
    public ResponseUtilisateurDto updateUtilisateur(CreateUtilisateurDto utilisateurDto, Integer id) {
        return utilisateurServiceHelper.updateUser(utilisateurDto,id);
    }

    @Override
    public ResponseUtilisateurDto updateUtilisateurByAdmin(CreateUtilisateurDto utilisateurDto, Principal principal) {
        return utilisateurServiceHelper.updateUserByAdmin(utilisateurDto,principal);
    }
}
