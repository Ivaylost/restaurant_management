package bg.softuni.restaurants_management.config;

import bg.softuni.restaurants_management.model.enums.RoleEnums;
import bg.softuni.restaurants_management.repository.UserRepository;
import bg.softuni.restaurants_management.service.impl.RestaurantsManagementUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/statics/**","/coordinates/**", "/admin/restaurants/details/**").permitAll()
                        .requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll()
                        .requestMatchers("/admin/**").hasRole(RoleEnums.ADMIN.name())
                        .requestMatchers("/manage/**").hasRole(RoleEnums.MANAGER.name())
                        .requestMatchers("/userReservation/**", "/makeReservation/**").hasAnyRole(RoleEnums.ADMIN.name(), RoleEnums.MANAGER.name(), RoleEnums.USER.name())
                        .anyRequest().permitAll()
        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/users/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            .logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new RestaurantsManagementUserDetailsService(userRepository);
    }
}
