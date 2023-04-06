package fr.eni.encheres.service;

import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import io.vavr.Tuple2;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface UtilisateurService {
    Tuple2<CreateUtilisateurDto, HttpHeaders> saveUtilisateur(@Valid CreateUtilisateurDto utilisateurDto);
    CreateUtilisateurDto findById(Integer id);

    List<CreateUtilisateurDto> findAll();

    void delete(Integer id);
}


