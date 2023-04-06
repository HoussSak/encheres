package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "RETRAITS")
public class Retrait extends AbstractEntity {
    @Embedded
    private Adresse adresse;
    @OneToOne
    @JoinColumn(name = "article_vendu_id")
    private ArticleVendu articleVendu;
}