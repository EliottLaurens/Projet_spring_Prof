package fr.limayrac.ProjetSpring.repository;

import fr.limayrac.ProjetSpring.model.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
    // Vous pouvez ajouter ici des méthodes de requête personnalisées si nécessaire
    List<Declaration> findByStatusIn(List<String> status);
}
