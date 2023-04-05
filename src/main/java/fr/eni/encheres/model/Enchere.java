package fr.eni.encheres.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "encheres")
public class Enchere extends AbstractEntity{
    private Instant dateEnchere;
    private BigDecimal montantEnchere;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur UtilisateurEnchere;
    @ManyToOne
    @JoinColumn(name = "article_vendu_id")
    private ArticleVendu articleVendu;
}
