package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantUpdateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.service.ImageService;
import bg.softuni.restaurants_management.service.RestaurantService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ModelMapper modelMapper, ImageService imageService) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
        this.imageService = imageService;
    }


    @Override
    public Restaurant createRestaurant(RestaurantCreateBindingModel restaurantCreateBindingModel) throws IOException {
        Restaurant restaurant = modelMapper.map(restaurantCreateBindingModel, Restaurant.class);
        restaurant.setActive(false);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        String[] suffix = restaurantCreateBindingModel.getFile().getOriginalFilename().split("\\.");
        String pathToSaveImage = "src\\main\\resources\\static\\images\\Restaurant_" + savedRestaurant.getId()
                + "\\primaryImage." + suffix[1];
        String pathToSaveInRestaurant = "\\images\\Restaurant_" + savedRestaurant.getId()
                + "\\primaryImage." + suffix[1];
        updateImgUrl(savedRestaurant, pathToSaveInRestaurant);
        imageService.saveImageIntoFileSystem(restaurantCreateBindingModel.getFile(), pathToSaveImage);
        return savedRestaurant;
    }

    @Override
    public RestaurantViewDetails getRestaurantViewDetailsByRestaurantId(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return modelMapper.map(restaurant, RestaurantViewDetails.class);
    }

    @Override
    public RestaurantUpdateBindingModel getRestaurantBindingModelDetailsByRestaurantId(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return modelMapper.map(restaurant, RestaurantUpdateBindingModel.class);
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

    @Override
    @Transactional
    public Restaurant updateRestaurant(RestaurantUpdateBindingModel restaurantUpdateBindingModel, Long id) throws IOException {
        Restaurant restaurant = modelMapper.map(restaurantUpdateBindingModel, Restaurant.class);
        Optional<Restaurant> updatedRestaurant = restaurantRepository.findById(id);
        if (restaurantUpdateBindingModel.getFile().getSize() > 0) {
            String path = "src\\main\\resources\\static" + updatedRestaurant.get().getImgUrl();
            imageService.saveImageIntoFileSystem(restaurantUpdateBindingModel.getFile(), path);
        }
        restaurant.setImgUrl(updatedRestaurant.get().getImgUrl());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void updateImgUrl(Restaurant restaurant, String pathToSaveImage) {
        restaurant.setImgUrl(pathToSaveImage);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(Long id) throws IOException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()){
            imageService.delete(restaurant.get());
            restaurantRepository.deleteById(id);
        }
    }
}
