package bg.softuni.restaurants_management.repository;

import bg.softuni.restaurants_management.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<UserEntity, Long> {
}
