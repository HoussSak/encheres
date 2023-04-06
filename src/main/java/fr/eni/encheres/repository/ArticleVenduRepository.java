package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleVendu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleVenduRepository extends JpaRepository<ArticleVendu, Integer> {
}
