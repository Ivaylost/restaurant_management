package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.TableCreateBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.service.ManageTableService;
import bg.softuni.restaurants_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manage/tables")
public class TableController {
    private final ManageTableService manageTableService;
    private final UserService userService;

    public TableController(ManageTableService manageTableService, UserService userService) {
        this.manageTableService = manageTableService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public ModelAndView create(
            @ModelAttribute("tableCreateBindingModel") TableCreateBindingModel tableCreateBindingModel
    ) {
        UserEntity loggedUser = getLoggedUser();
        if (loggedUser == null){
            new ModelAndView("redirect:/users/login");
        }

        List<RestaurantViewDetails> restaurantViewDetailsList = userService.getUsersRestaurants(loggedUser);
        ModelAndView view = new ModelAndView("create-table");
        view.addObject("restaurantViewDetailsList", restaurantViewDetailsList);

        return view;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute("tableCreateBindingModel") @Valid TableCreateBindingModel tableCreateBindingModel,
                               BindingResult bindingResult) {
        UserEntity loggedUser = getLoggedUser();
        if (loggedUser == null){
            new ModelAndView("redirect:/users/login");
        }

        if (bindingResult.hasErrors()) {
            List<RestaurantViewDetails> restaurantViewDetailsList = userService.getUsersRestaurants(loggedUser);
            ModelAndView view = new ModelAndView("create-table");
            view.addObject("restaurantViewDetailsList", restaurantViewDetailsList);
            return view;
        }

        manageTableService.createTable(tableCreateBindingModel);

        return new ModelAndView("redirect:/manage/tables/all");
    }

    @GetMapping("/all")
    public ModelAndView all() {
        UserEntity loggedUser = getLoggedUser();
        if (loggedUser == null){
            new ModelAndView("redirect:/users/login");
        }

        List<RestaurantViewDetails> restaurantViewDetailsList = userService.getUsersRestaurants(loggedUser);
        ModelAndView view = new ModelAndView("user_restaurant_details");
        view.addObject("restaurantViewDetailsList", restaurantViewDetailsList);
        return view;
    }

    @GetMapping("/removeTable/{tableId}")
    public ModelAndView removeTable(@PathVariable("tableId") Long tableId){
        manageTableService.removeTable(tableId);
        return new ModelAndView("redirect:/manage/tables/all");
    }

    private UserEntity getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<UserEntity> optionalUser = userService.findUserByEmail(email);
        return optionalUser.orElse(null);
    }
}
