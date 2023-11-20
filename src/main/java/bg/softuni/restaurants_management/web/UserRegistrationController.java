package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.UserRegistrationBindingModel;
import bg.softuni.restaurants_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationBindingModel") @Valid UserRegistrationBindingModel userRegistrationBindingModel,
                           BindingResult bindingResult) {



        if (bindingResult.hasErrors()) {
            return new ModelAndView("auth-register");
        }

        boolean hasSuccessfulRegistration = userService.registerUser(userRegistrationBindingModel);

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("auth-register");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }
}
