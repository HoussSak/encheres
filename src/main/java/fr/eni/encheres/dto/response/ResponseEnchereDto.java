package fr.eni.encheres.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ResponseEnchereDto {
    private LocalDate dateEnchere;

    private Integer montantEnchere;

    private String acheteur;
}
