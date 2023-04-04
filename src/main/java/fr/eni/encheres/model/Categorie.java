package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "categories")
public class Categorie extends AbstractEntity {
    private String libelle;
    @OneToMany(mappedBy = "articleCategorie")
    private List<ArticleVendu> articles;
}
