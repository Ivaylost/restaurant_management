package bg.softuni.restaurants_management.model.dto;

public class TableViewDetails {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public TableViewDetails setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TableViewDetails setName(String name) {
        this.name = name;
        return this;
    }
}
