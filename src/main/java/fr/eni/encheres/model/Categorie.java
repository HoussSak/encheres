package fr.eni.encheres.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "CATEGORIES")
public class Categorie extends AbstractEntity {
    private String libelle;
}
