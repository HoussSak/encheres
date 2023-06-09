package fr.eni.encheres.repository;

import fr.eni.encheres.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Categorie,Integer> {
    Categorie findByLibelle(String libelle);
}
