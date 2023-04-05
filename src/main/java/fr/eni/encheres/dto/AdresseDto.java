package fr.eni.encheres.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdresseDto {
    @Column(name = "rue")
    @NotBlank(message = "Le champ rue est obligatoire")
    private String rue;
    @Column(name = "codepostal")
    @NotBlank(message = "Le champ code postal est obligatoire")
    @Size(min = 4,max = 5,message = "le code postal est invalide ! ")
    private String codePostal;
    @Column(name = "ville")
    @NotBlank(message = "Le champ ville est obligatoire")
    private String ville;

}
