package bg.softuni.restaurants_management.event;

import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private final String registrationLink;

    public UserRegistrationEvent(Object source, String registrationLink) {
        super(source);
        this.registrationLink=registrationLink;
    }

    public String getRegistrationLink() {
        return registrationLink;
    }
}
