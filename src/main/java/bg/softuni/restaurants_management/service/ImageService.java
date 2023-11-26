package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantUpdateBindingModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImageIntoFileSystem(MultipartFile file, String imageUrl);


//    String updateImageIntoFileSystem(RestaurantUpdateBindingModel restaurantUpdateBindingModel) throws IOException;
}
