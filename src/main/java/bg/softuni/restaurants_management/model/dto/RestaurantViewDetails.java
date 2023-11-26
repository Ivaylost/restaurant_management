package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class RestaurantViewDetails {
    private Long id;
    private String name;

    private String lon;

    private String lat;

    private String imgUrl;

    private Boolean isActive;
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public RestaurantViewDetails setFile(MultipartFile file) {
        this.file = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantViewDetails setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public RestaurantViewDetails setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public RestaurantViewDetails setId(Long id) {
        this.id = id;
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
}
