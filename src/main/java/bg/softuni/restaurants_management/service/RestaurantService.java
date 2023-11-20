package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.UploadImgDto;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
}

