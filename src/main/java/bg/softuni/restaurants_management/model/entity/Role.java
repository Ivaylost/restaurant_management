package bg.softuni.restaurants_management.model.entity;

import bg.softuni.restaurants_management.model.enums.RoleEnums;
import jakarta.persistence.*;

@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnums role;

    public RoleEnums getRole() {
        return role;
    }

    public Role setRole(RoleEnums role) {
        this.role = role;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }
}
