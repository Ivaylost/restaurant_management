package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.service.ImageService;
import bg.softuni.restaurants_management.service.RestaurantService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    public RestaurantController(RestaurantService restaurantService, ImageService imageService, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView create(
            @ModelAttribute("restaurantCreateBindingModel") RestaurantCreateBindingModel restaurantCreateBindingModel
    ) {
        return new ModelAndView("restaurant-create");
    }

    @PostMapping("/create")
    public ModelAndView register(@ModelAttribute("restaurantCreateBindingModel") @Valid RestaurantCreateBindingModel restaurantCreateBindingModel,
                                 BindingResult bindingResult) {

        String imageUrl = imageService.saveImageIntoFileSystem(restaurantCreateBindingModel);

        if (bindingResult.hasErrors() || imageUrl == null) {
            return new ModelAndView("restaurant-create");
        }

        Restaurant restaurant = modelMapper.map(restaurantCreateBindingModel, Restaurant.class);
        restaurant.setImgUrl(imageUrl);
        boolean hasSuccessfulRegistration = restaurantService.createRestaurant(restaurant) != null;

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("restaurant-create");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }
        String redirectUrl = "redirect:/restaurants/details/" + restaurant.getId();
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
    public ModelAndView allRestaurants(){
        List<RestaurantViewDetails> restaurants = restaurantService.getAllRestaurants();
        ModelAndView view = new ModelAndView("restaurants");
        view.addObject("restaurants", restaurants);
        return view;
    }
}
