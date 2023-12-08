package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.TableCreateBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.service.ReservationService;
import bg.softuni.restaurants_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RequestMapping("/reservation")
@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/init")
    public ModelAndView create(
            @ModelAttribute("createAllReservationsDateBindingModel") CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel
    ) {
        UserEntity loggedUser = getLoggedUser();
        if (loggedUser == null) {
            new ModelAndView("redirect:/users/login");
        }

        List<RestaurantViewDetails> restaurantViewDetailsList = userService.getUsersRestaurants(loggedUser);
        ModelAndView view = new ModelAndView("init-reservations");
        view.addObject("restaurantViewDetailsList", restaurantViewDetailsList);
        return view;
    }

    @PostMapping("/init")
    public ModelAndView create(@ModelAttribute("createAllReservationsDateBindingModel")
                               CreateAllReservationsDateBindingModel createAllReservationsDateBindingModel,
                               BindingResult bindingResult) {
        UserEntity loggedUser = getLoggedUser();
        if (loggedUser == null) {
            new ModelAndView("redirect:/users/login");
        }

        reservationService.initReservationByRestaurant(createAllReservationsDateBindingModel);
        return new ModelAndView("redirect:/manage/tables/all");
    }

    private UserEntity getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.findUserByEmail(email);
    }
}
