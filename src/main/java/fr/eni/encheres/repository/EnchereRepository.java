package fr.eni.encheres.repository;

import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.EnchereId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere, EnchereId> {
    Enchere findFirstByArticleVenduOrderByMontantEnchereDesc(ArticleVendu articleVendu);
}
