package fr.eni.encheres.repository;

import fr.eni.encheres.model.Retrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetraitRepository extends JpaRepository<Retrait,Integer> {
}
