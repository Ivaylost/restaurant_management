package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.entity.Role;
import bg.softuni.restaurants_management.model.entity.UserEntity;
import bg.softuni.restaurants_management.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RestaurantsManagementUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public RestaurantsManagementUserDetailsService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(RestaurantsManagementUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private static UserDetails map(UserEntity user){
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(RestaurantsManagementUserDetailsService::map).toList())
                .build();

    }

    private static GrantedAuthority map(Role role){
        return new SimpleGrantedAuthority(role.getRole().name());
    }
}
