package bg.softuni.restaurants_management.service;

import java.util.UUID;

public class TokenProviderImpl implements TokenProvider {

    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }
}
