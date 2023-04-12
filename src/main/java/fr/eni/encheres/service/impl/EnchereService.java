package fr.eni.encheres.service.impl;


import fr.eni.encheres.dto.response.ResponseEnchereDto;
import fr.eni.encheres.model.Enchere;

import java.security.Principal;
import java.util.List;

public interface EnchereService {
    void addNewEnchere(Integer idArticle,Integer montant, Principal principal);

    List<ResponseEnchereDto> findAllEnchere(Integer idEnchere);
}
