package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "retraits")
public class Retrait extends AbstractEntity {
    @Embedded
    private Adresse adresse;
    @OneToOne
    @JoinColumn(name = "article_vendu_id",unique = true)
    private ArticleVendu articleVendu;
}