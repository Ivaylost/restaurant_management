package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.UploadImgDto;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantService {
    boolean createRestaurant(RestaurantCreateBindingModel restaurantCreateBindingModel);

    void saveImage(MultipartFile imageFile, UploadImgDto imgDto);
}

