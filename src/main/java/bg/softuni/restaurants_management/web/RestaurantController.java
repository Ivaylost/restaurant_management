package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantUpdateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/create")
    public ModelAndView create(
            @ModelAttribute("restaurantCreateBindingModel") RestaurantCreateBindingModel restaurantCreateBindingModel
    ) {
        return new ModelAndView("restaurant-create");
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("restaurantCreateBindingModel") @Valid RestaurantCreateBindingModel restaurantCreateBindingModel,
                               BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("restaurant-create");
        }

        Restaurant savedRestaurant = restaurantService.createRestaurant(restaurantCreateBindingModel);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/details/{id}")
    public ModelAndView restaurantDetails(@PathVariable("id") Long id) {
        RestaurantViewDetails restaurantViewDetails = restaurantService.getRestaurantViewDetailsByRestaurantId(id);
        ModelAndView view = new ModelAndView("restaurant_details");
        view.addObject("restaurantViewDetails", restaurantViewDetails);
        return view;
    }

    @GetMapping("all")
    public ModelAndView allRestaurants() {
        List<RestaurantViewDetails> restaurants = restaurantService.getAllRestaurants();
        ModelAndView view = new ModelAndView("restaurants");
        view.addObject("restaurants", restaurants);
        return view;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id) {
        RestaurantUpdateBindingModel restaurantUpdateBindingModel = restaurantService.getRestaurantBindingModelDetailsByRestaurantId(id);
        ModelAndView view = new ModelAndView("restaurant-update");
        view.addObject("restaurantUpdateBindingModel", restaurantUpdateBindingModel);
        return view;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@ModelAttribute("restaurantUpdateBindingModel") @Valid RestaurantUpdateBindingModel restaurantUpdateBindingModel,
                               BindingResult bindingResult, @PathVariable("id") Long id) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("restaurant-update");
        }

        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantUpdateBindingModel, id);
        return new ModelAndView("redirect:/");
    }
}
