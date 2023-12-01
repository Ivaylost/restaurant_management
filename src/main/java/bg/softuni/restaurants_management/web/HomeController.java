package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.dto.ReservationViewModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.service.ReservationService;
import bg.softuni.restaurants_management.service.RestaurantService;
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
public class HomeController {
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;
    private final UserService userService;

    public HomeController(RestaurantService restaurantService, ReservationService reservationService, UserService userService) {
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index(){
        List<RestaurantViewDetails> allRestaurants = restaurantService.getAllRestaurants();
        ModelAndView view = new ModelAndView("index");
        view.addObject("allRestaurants", allRestaurants);
        return view;
    }

    @GetMapping("userReservation/{restaurantId}")
    public ModelAndView userReservation(@ModelAttribute("createAllReservationsDateBindingModel")
                                            CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel,
            @PathVariable("restaurantId") Long restaurantId
    ){
        List<RestaurantViewDetails> allRestaurants = restaurantService.getAllRestaurants();
        String restaurantName = restaurantService.getRestaurantName(restaurantId);
        ModelAndView view = new ModelAndView("create-reservation");
        view.addObject("restaurantId", restaurantId);
        view.addObject("restaurantName", restaurantName);
        return view;
    }

    @PostMapping("/userReservation/{restaurantId}")
    public ModelAndView userReservation(
            @ModelAttribute("createAllReservationsDateBindingModel") @Valid CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel,
            BindingResult bindingResult,  @PathVariable("restaurantId") Long restaurantId
            ){

        List<ReservationViewModel> reservationViews = reservationService.findAllByDateIsAndUser_IdAndTable_Restaurant_Id(
                restaurantId, null, createAllReservationsDateBindingModel.getDatepicker());
        ModelAndView view = new ModelAndView("make-reservation");
        view.addObject("reservationViews", reservationViews);
        return view;
    }

    @PostMapping("/makeReservation")
    public ModelAndView makeReservation(@RequestParam Long reservationId){

        String userEmail = getLoggedUser().getEmail();

        reservationService.makeReservation(reservationId, userEmail);

        return new ModelAndView("redirect:/");
    }

    private UserEntity getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<UserEntity> optionalUser = userService.findUserByEmail(email);
        return optionalUser.orElse(null);
    }
}
