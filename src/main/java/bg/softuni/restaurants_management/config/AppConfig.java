package bg.softuni.restaurants_management.config;

import bg.softuni.restaurants_management.service.TokenProvider;
import bg.softuni.restaurants_management.service.TokenProviderImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public TokenProvider tokenProvider(){
        return new TokenProviderImpl();
    }
}
