package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.repository.AdminTableRepository;
import bg.softuni.restaurants_management.service.AdminTableService;
import org.springframework.stereotype.Service;

@Service
public class AdminTableServiceImpl implements AdminTableService {
    private final AdminTableRepository adminTableRepository;

    public AdminTableServiceImpl(AdminTableRepository adminTableRepository) {
        this.adminTableRepository = adminTableRepository;
    }
}
