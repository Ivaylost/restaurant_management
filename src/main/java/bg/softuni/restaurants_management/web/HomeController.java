package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.CreateAllReservationsDateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.entity.Reservation;
import bg.softuni.restaurants_management.service.ReservationService;
import bg.softuni.restaurants_management.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    public HomeController(RestaurantService restaurantService, ReservationService reservationService) {
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
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

        List<Reservation> reservations = reservationService.findAllByDateIsAndUser_IdAndTable_Restaurant_Id(restaurantId, null, createAllReservationsDateBindingModel.getDatepicker());
//        List<RestaurantViewDetails> allRestaurants = restaurantService.getAllRestaurants();
//        String restaurantName = restaurantService.getRestaurantName(restaurantId);
//        ModelAndView view = new ModelAndView("create-reservation");
//        view.addObject("restaurantId", restaurantId);
//        view.addObject("restaurantName", restaurantName);
        return new ModelAndView();
    }
}
