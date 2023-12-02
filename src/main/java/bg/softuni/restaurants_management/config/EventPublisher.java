package bg.softuni.restaurants_management.config;

import bg.softuni.restaurants_management.event.UserRegistrationEvent;
import bg.softuni.restaurants_management.service.EventPublisherInterface;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher implements EventPublisherInterface {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishUserRegistrationEvent(String registrationLink) {
        UserRegistrationEvent event = new UserRegistrationEvent(this, registrationLink);
        applicationEventPublisher.publishEvent(event);
    }
}
