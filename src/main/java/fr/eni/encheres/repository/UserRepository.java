package fr.eni.encheres.repository;

import fr.eni.encheres.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findOneByEmail(String email);
}
