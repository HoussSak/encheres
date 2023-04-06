package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "encheres")
public class Enchere{
    @EmbeddedId
    private EnchereId id;
    @Column(name = "date_enchere")
    private LocalDateTime dateEnchere;
    @Column(name = "montant_enchere")
    private Integer montantEnchere;
    @ManyToOne
    @MapsId("utilisateurId")
    @JoinColumn(name = "no_utilisateur")
    private Utilisateur utilisateur;
    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "no_article")
    private ArticleVendu articleVendu;
}
