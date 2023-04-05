package fr.eni.encheres.service;

import fr.eni.encheres.dto.UtilisateurDto;
import fr.eni.encheres.model.Utilisateur;
import io.vavr.Tuple2;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface UtilisateurService {
    Tuple2<UtilisateurDto, HttpHeaders> saveUtilisateur(@Valid UtilisateurDto utilisateurDto);
    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);
}


