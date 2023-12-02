package bg.softuni.restaurants_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestaurantsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsManagementApplication.class, args);
	}

}
