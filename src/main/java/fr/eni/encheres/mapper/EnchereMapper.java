package fr.eni.encheres.mapper;

import fr.eni.encheres.dto.response.ResponseEnchereDto;
import fr.eni.encheres.model.Enchere;
import org.springframework.stereotype.Component;

@Component
public class EnchereMapper {
    public static ResponseEnchereDto enchereToResponseEnchereDto(Enchere enchere) {
        if (enchere == null) {
            return ResponseEnchereDto.builder().build();
        }
        return ResponseEnchereDto.builder()
                .id(enchere.getUtilisateur().getId())
                .montantEnchere(enchere.getMontantEnchere())
                .dateEnchere(enchere.getDateEnchere())
                .acheteur(enchere.getUtilisateur().getPseudo())
                .build();
    }
}
