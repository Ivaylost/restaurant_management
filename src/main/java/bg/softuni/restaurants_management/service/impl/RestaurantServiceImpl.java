package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.UploadImgDto;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean createRestaurant(RestaurantCreateBindingModel restaurantCreateBindingModel) {
        return false;
    }

    @Override
    public void saveImage(MultipartFile imageFile, UploadImgDto imgDto) {

    }
}
