package fr.eni.encheres.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnchereId implements Serializable {

    @Column(name = "no_utilisateur")
    private Integer utilisateurId;

    @Column(name = "no_article")
    private Integer articleId;

    // constructeurs, getters, setters, equals, hashCode

}
