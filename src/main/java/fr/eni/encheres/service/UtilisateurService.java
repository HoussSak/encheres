package fr.eni.encheres.service;

import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import io.vavr.Tuple2;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;

import java.security.Principal;
import java.util.List;

public interface UtilisateurService {
    Tuple2<ResponseUtilisateurDto, HttpHeaders> saveUtilisateur(@Valid CreateUtilisateurDto utilisateurDto);
    ResponseUtilisateurDto findById(Integer id);

    List<ResponseUtilisateurDto> findAll();

    void delete(Principal principal);
    void deleteAccountByAdmin(Integer id);
    ResponseUtilisateurDto updateUtilisateurByAdmin(@Valid CreateUtilisateurDto utilisateurDto, Integer id);
    Tuple2<ResponseUtilisateurDto, HttpHeaders> updateUtilisateur(@Valid CreateUtilisateurDto utilisateurDto, Principal principal);
}


