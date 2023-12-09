package bg.softuni.restaurants_management.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double lon;

    @Column(nullable = false)
    private Double lat;

    @Column()
    private String imgUrl;

    private Boolean isActive;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "restaurant"
    )
    private List<TableEntity> tableEntities = new ArrayList<>();

    public List<TableEntity> getTableEntities() {
        return tableEntities;
    }

    public Restaurant setTableEntities(List<TableEntity> tableEntities) {
        this.tableEntities = tableEntities;
        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLon() {
        return lon;
    }

    public Restaurant setLon(Double lon) {
        this.lon = lon;
        return this;
    }

    public Double getLat() {
        return lat;
    }

    public Restaurant setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Restaurant setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Restaurant setActive(Boolean active) {
        isActive = active;
        return this;
    }
}
