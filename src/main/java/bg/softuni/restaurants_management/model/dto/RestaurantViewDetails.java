package bg.softuni.restaurants_management.model.dto;

import bg.softuni.restaurants_management.model.entity.TableEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RestaurantViewDetails {
    private Long id;
    private String name;
    private String lon;
    private String lat;
    private Boolean isActive;
    private List<TableViewDetails> tableViewDetails;

    public List<TableViewDetails> getTableViewDetails() {
        return tableViewDetails;
    }

    public RestaurantViewDetails setTableViewDetails(List<TableEntity> tableEntities) {
        this.tableViewDetails = tableEntities.stream()
                .map(x -> new TableViewDetails().setId(x.getId()).setName(x.getName()))
                .toList();
        return this;
    }

    private String imgUrl;
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
