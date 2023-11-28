package bg.softuni.restaurants_management.service;

import bg.softuni.restaurants_management.model.dto.TableCreateBindingModel;

public interface ManageTableService {
    void createTable(TableCreateBindingModel tableCreateBindingModel);

    void removeTable(Long tableId);
}
