package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;

public interface ImageService {
    boolean save(RestaurantCreateBindingModel restaurantCreateBindingModel);
}
