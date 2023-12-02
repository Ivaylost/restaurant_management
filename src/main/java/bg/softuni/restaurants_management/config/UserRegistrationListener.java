package bg.softuni.restaurants_management.config;

import bg.softuni.restaurants_management.event.UserRegistrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {
    @EventListener
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        String registrationLink = event.getRegistrationLink();
        System.out.println("User registered. Registration link: " + registrationLink);
    }
}
