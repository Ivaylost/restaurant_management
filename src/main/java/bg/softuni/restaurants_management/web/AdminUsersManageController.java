package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.UserDto;
import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        List<Role> unassignedRoles = adminUserService.getUnassignedRoles(id);
        ModelAndView view = new ModelAndView("user-details");
        view.addObject("user", user);
        view.addObject("unassignedRoles", unassignedRoles);
        return view;
    }

    @PostMapping("/assignRole/{user_id}")
    public ModelAndView assignRole(@PathVariable("user_id") Long user_id,
                                   @RequestParam("role_id") Long role_id) {
        if(role_id ==0){
            return new ModelAndView("redirect:/admin/users/details/" + user_id);
        }
        adminUserService.assignRole(user_id, role_id);
        return new ModelAndView("redirect:/admin/users/details/" + user_id);
    }

    @DeleteMapping("/deleteRole/{user_id}/{role_id}")
    public ModelAndView deleteRole(@PathVariable("user_id") Long user_id,
                                   @PathVariable("role_id") Long role_id){
        adminUserService.delete(user_id, role_id);
        return new ModelAndView("redirect:/admin/users/details/" + user_id);
    }
}
