package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersManageController {

    private final AdminUserService adminUserService;

    public AdminUsersManageController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("all")
    public ModelAndView allRestaurants() {
        List<UserDto> users = adminUserService.getAllUsers();
        ModelAndView view = new ModelAndView("all-users");
        view.addObject("users", users);
        return view;
    }

    @GetMapping("/details/{id}")
    public ModelAndView userDetails(@PathVariable("id") Long id) {
        UserDto user = adminUserService.getUserById(id);
        ModelAndView view = new ModelAndView("user-details");
        view.addObject("user", user);
        return view;
    }
}
