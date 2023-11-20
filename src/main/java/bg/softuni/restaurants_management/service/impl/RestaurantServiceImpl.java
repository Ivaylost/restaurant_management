package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantViewDetails getRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return restaurant.map(RestaurantViewDetails::new).orElse(null);
    }
}
