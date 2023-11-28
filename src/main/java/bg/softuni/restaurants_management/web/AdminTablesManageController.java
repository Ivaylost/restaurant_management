package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.service.AdminTableService;
import org.springframework.stereotype.Controller;

@Controller
public class AdminTablesManageController {
    private final AdminTableService adminTableService;

    public AdminTablesManageController(AdminTableService adminTableService) {
        this.adminTableService = adminTableService;
    }
}
