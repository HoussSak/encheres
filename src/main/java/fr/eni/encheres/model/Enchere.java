package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "encheres")
public class Enchere{
    @EmbeddedId
    private EnchereId id;
    @Column(name = "date_enchere")
    private LocalDate dateEnchere;
    @Column(name = "montant_enchere")
    private Integer montantEnchere;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("utilisateurId")
    @JoinColumn(name = "no_utilisateur")
    private Utilisateur utilisateur;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articleId")
    @JoinColumn(name = "no_article")
    private ArticleVendu articleVendu;
}
