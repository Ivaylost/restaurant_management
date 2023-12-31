package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
        List<Role> unassignedRoles = adminUserService.getUnassignedRoles(id);
        List<RestaurantViewDetails> unassignedRestaurants = adminUserService.getUnassignedRestaurants(id);
        ModelAndView view = new ModelAndView("user-details");
        view.addObject("user", user);
        view.addObject("unassignedRoles", unassignedRoles);
        if (user.isManager()){
            view.addObject("unassignedRestaurants", unassignedRestaurants);
        } else {
            view.addObject("unassignedRestaurants", new ArrayList<Restaurant>());
        }

        return view;
    }

    @PostMapping("/assignRole/{user_id}")
    public ModelAndView assignRole(@PathVariable("user_id") Long user_id,
                                   @RequestParam("role_id") Long role_id) {
        UserDto user = adminUserService.getUserById(user_id);
        if(role_id ==0){
            return new ModelAndView("redirect:/admin/users/details/" + user_id);
        }
        adminUserService.assignRole(user_id, role_id);
        return new ModelAndView("redirect:/admin/users/details/" + user_id);
    }

    @GetMapping("/removeRole/{user_id}/{role_id}")
    public ModelAndView deleteRole(@PathVariable("user_id") Long user_id,
                                   @PathVariable("role_id") Long role_id){
        adminUserService.removeRole(user_id, role_id);
        return new ModelAndView("redirect:/admin/users/details/" + user_id);
    }

    @PostMapping("/assignRestaurant/{user_id}")
    public ModelAndView assignRestaurant(@PathVariable("user_id") Long user_id,
                                   @RequestParam("restaurant_id") Long restaurant_id) {
        UserDto user = adminUserService.getUserById(user_id);
        if(restaurant_id ==0){
            return new ModelAndView("redirect:/admin/users/details/" + user_id);
        }
        adminUserService.assignRestaurant(user_id, restaurant_id);
        return new ModelAndView("redirect:/admin/users/details/" + user_id);
    }

    @PostMapping("/unassignRestaurant/{user_id}/{restaurant_id}")
    public ModelAndView unassignedRestaurant(@PathVariable("user_id") Long user_id,
                                         @PathVariable("restaurant_id") Long restaurant_id) {
        if(restaurant_id ==0){
            return new ModelAndView("redirect:/admin/users/details/" + user_id);
        }
        adminUserService.unassignRestaurant(user_id, restaurant_id);
        return new ModelAndView("redirect:/admin/users/details/" + user_id);
    }

    @GetMapping("/findByEmail")
    public ModelAndView findByEmail() {
        return new ModelAndView("find-user");
    }

    @PostMapping("/findByEmail")
    public ModelAndView findByEmail(@RequestParam("email") String email) {
        Long userId = adminUserService.getUserByEmail(email);
        return new ModelAndView("redirect:/admin/users/details/" + userId);
    }
}
