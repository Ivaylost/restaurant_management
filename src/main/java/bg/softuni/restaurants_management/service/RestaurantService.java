package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.*;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateBindingModel restaurantCreateBindingModel) throws IOException;

    RestaurantViewDetails getRestaurantViewDetailsByRestaurantId(Long id);
    RestaurantUpdateBindingModel getRestaurantBindingModelDetailsByRestaurantId(Long id);

    List<RestaurantViewDetails> getAllRestaurants();

    Restaurant updateRestaurant(RestaurantUpdateBindingModel restaurantUpdateBindingModel, Long id) throws IOException;

    void updateImgUrl(Restaurant restaurant, String fileName);

    void delete(Long id) throws IOException;

    void updateRestaurantsWithTable(TableCreateBindingModel tableCreateBindingModel);

    String getRestaurantName(Long restaurantId);

    List<Double> getCoordinates(Long id);
}

