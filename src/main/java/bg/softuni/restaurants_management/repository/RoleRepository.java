package bg.softuni.restaurants_management.repository;

import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.enums.RoleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleEnums role);
}
