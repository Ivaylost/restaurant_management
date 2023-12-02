package bg.softuni.restaurants_management.service;

public interface EventPublisherInterface {
    void publishUserRegistrationEvent(String registrationLink);
}
