package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ARTICLES_VENDUS")
public class ArticleVendu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_article")
    private Integer noArticle;
    @Column(name = "nom_article")
    private String nomArticle;
    private String description;
    @Column(name = "date_debut_encheres")
    private LocalDate dateDebutEncheres;
    @Column(name = "date_fin_encheres")
    private LocalDate dateFinEncheres;
    @Column(name = "prix_initial")
    private Integer prixInitial;
    @Column(name = "prix_vente")
    private Integer prixVente;
    @ManyToOne
    @JoinColumn(name = "no_utilisateur")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "categorie")
    private Categorie categorie;;
    @OneToOne(mappedBy = "articleVendu",cascade = CascadeType.ALL,optional = true)
    private Retrait retrait;
    @OneToMany(mappedBy = "articleVendu",cascade = CascadeType.ALL)
    private List<Enchere> encheres = new ArrayList<>();

}
