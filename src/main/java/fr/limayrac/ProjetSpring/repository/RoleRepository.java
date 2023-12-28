package fr.limayrac.ProjetSpring.repository;

import fr.limayrac.ProjetSpring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}