package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.error.ObjectNotFoundException;
import bg.softuni.restaurants_management.model.dto.TableCreateBindingModel;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.TableRepository;
import bg.softuni.restaurants_management.service.ManageTableService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageTableServiceImpl implements ManageTableService {
    private final TableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;

    public ManageTableServiceImpl(TableRepository tableRepository, RestaurantRepository restaurantRepository) {
        this.tableRepository = tableRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void createTable(TableCreateBindingModel tableCreateBindingModel) {
        TableEntity table = new TableEntity();
        table.setName(tableCreateBindingModel.getName());
        Restaurant restaurant = restaurantRepository.findById(tableCreateBindingModel.getRestaurantId()).get();
        table.setRestaurant(restaurant);
        tableRepository.save(table);
    }

    @Override
    public void removeTable(Long tableId) {
        Optional<TableEntity> optionalTableEntity = tableRepository.findById(tableId);
        if (optionalTableEntity.isEmpty()) {
            throw new ObjectNotFoundException("Object not found!");
        }
            tableRepository.delete(optionalTableEntity.get());
    }
}
