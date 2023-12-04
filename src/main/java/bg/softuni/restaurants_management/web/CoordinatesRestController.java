package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoordinatesRestController {
    private final RestaurantService restaurantService;

    public CoordinatesRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/coordinates/{id}")
    public List<Double> getRouteCoordinates(@PathVariable("id") Long id) {
        return restaurantService.getCoordinates(id);
    }
}
