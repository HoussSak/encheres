package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "articlevendus")
public class ArticleVendu extends AbstractEntity {
    private String nomArticle;
    private String description;
    private Instant dateDebutEncheres;
    private Instant dateFinEncheres;
    private BigDecimal prixInitial;
    private BigDecimal prixVente;
    @ManyToOne
    @JoinColumn(name = "acheteur_id")
    private Utilisateur acheteur;
    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private Utilisateur vendeur;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie articleCategorie;
    @OneToOne(mappedBy = "articleVendu",cascade = CascadeType.ALL,optional = true)
    private Retrait retrait;
    @OneToOne(mappedBy = "articleVendu",cascade = CascadeType.ALL,optional = true)
    private Enchere enchere;

}
