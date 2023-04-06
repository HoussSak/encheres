package fr.eni.encheres.repository;

import fr.eni.encheres.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categorie,Integer> {
}
