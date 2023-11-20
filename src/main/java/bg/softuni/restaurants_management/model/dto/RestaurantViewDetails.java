package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class RestaurantViewDetails {
    private String name;

    private String lon;

    private String lat;

    private String imgUrl;

    public String getName() {
        return name;
    }

    public RestaurantViewDetails setName(String name) {
        this.name = name;
        return this;
    }

    public String getLon() {
        return lon;
    }

    public RestaurantViewDetails setLon(String lon) {
        this.lon = lon;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public RestaurantViewDetails setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public RestaurantViewDetails setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public RestaurantViewDetails(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.lon = restaurant.getLon().toString();
        this.lat = restaurant.getLat().toString();
        this.imgUrl = restaurant.getImgUrl();
    }
}
