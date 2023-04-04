package fr.eni.encheres.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Adresse {
    @Column(name = "rue")
    private String rue;
    @Column(name = "codepostal")
    private String codePostal;
    @Column(name = "ville")
    private String ville;

}
