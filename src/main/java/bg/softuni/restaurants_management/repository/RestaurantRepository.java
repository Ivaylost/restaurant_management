package bg.softuni.restaurants_management.repository;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String value);
    List<Restaurant> findAllBy();
    List<Restaurant> findAllByIsActiveIs(Boolean value);
}
