package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantViewDetails getRestaurantViewDetailsByRestaurantId(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return modelMapper.map(restaurant, RestaurantViewDetails.class);
    }

    @Override
    public List<RestaurantViewDetails> getAllRestaurants() {

        return restaurantRepository.findAllBy()
                .stream()
                .map(restaurant ->
                        modelMapper.map(restaurant, RestaurantViewDetails.class)
                )
                .toList();
    }
}
