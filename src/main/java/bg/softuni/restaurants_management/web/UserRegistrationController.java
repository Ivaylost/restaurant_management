package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(
            @ModelAttribute("userRegistrationBindingModel") UserRegistrationBindingModel userRegistrationBindingModel
    ){
        return new ModelAndView("auth-register");
    }

    @GetMapping("/verify")
    public ModelAndView verify(
            @RequestParam String token
    ){
        userService.verifyUser(token);
        return new ModelAndView("redirect:/users/login");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationBindingModel") @Valid UserRegistrationBindingModel userRegistrationBindingModel,
                           BindingResult bindingResult) {



        if (bindingResult.hasErrors()) {
            return new ModelAndView("auth-register");
        }

        UserEntity user = userService.registerUser(userRegistrationBindingModel);

        return new ModelAndView("redirect:/users/login");
    }
}