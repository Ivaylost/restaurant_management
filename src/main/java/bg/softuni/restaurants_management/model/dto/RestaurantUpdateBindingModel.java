package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.validators.UniqueRestaurantName;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class RestaurantUpdateBindingModel {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lon;
    @NotEmpty
    private String lat;

    private Boolean isActive;

    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public RestaurantUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getLon() {
        return lon;
    }

    public RestaurantUpdateBindingModel setLon(String lon) {
        this.lon = lon;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public RestaurantUpdateBindingModel setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public RestaurantUpdateBindingModel setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public MultipartFile getFile() {
        return file;
    }

    public RestaurantUpdateBindingModel setFile(MultipartFile file) {
        this.file = file;
        return this;
    }
}
