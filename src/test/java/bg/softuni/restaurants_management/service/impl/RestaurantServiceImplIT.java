package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.error.ObjectNotFoundException;
import bg.softuni.restaurants_management.model.dto.RestaurantUpdateBindingModel;
import bg.softuni.restaurants_management.model.dto.RestaurantViewDetails;
import bg.softuni.restaurants_management.model.dto.TableCreateBindingModel;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.model.entity.TableEntity;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
import bg.softuni.restaurants_management.repository.TableRepository;
import bg.softuni.restaurants_management.service.ImageService;
import bg.softuni.restaurants_management.service.RestaurantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplIT {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private TableRepository tableRepository;

    @Value("restaurant")
    private String restaurantName;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();
        TableEntity table = new TableEntity().setName("Test");
        Restaurant restaurant = new Restaurant().setName(restaurantName)
                .setImgUrl("url")
                .setActive(false)
                .setLat(20D)
                .setLon(30D);
        restaurant.getTableEntities().add(table);
        restaurantRepository.save(restaurant);
    }

    @AfterEach
    void tearDown() {
        restaurantRepository.deleteAll();
    }

    @Test
    void testGetRestaurantViewDetailsByRestaurantId() throws IOException {

        Optional<Restaurant> byName = restaurantRepository.findByName(restaurantName);

        RestaurantViewDetails restaurantViewDetailsByRestaurantId = restaurantService.getRestaurantViewDetailsByRestaurantId(byName.get().getId());

        assertInstanceOf(RestaurantViewDetails.class, restaurantViewDetailsByRestaurantId);
        assertEquals(byName.get().getName(), restaurantViewDetailsByRestaurantId.getName());
    }

    @Test
    void testGetRestaurantViewDetailsByRestaurantIdThrowObjectNotFoundExc(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.getRestaurantViewDetailsByRestaurantId(100L));
    }

    @Test
    void testGetRestaurantBindingModelDetailsByRestaurantId() throws IOException {

        Optional<Restaurant> byName = restaurantRepository.findByName(restaurantName);

        RestaurantUpdateBindingModel restaurantUpdateBindingModel = restaurantService.getRestaurantBindingModelDetailsByRestaurantId(byName.get().getId());

        assertInstanceOf(RestaurantUpdateBindingModel.class, restaurantUpdateBindingModel);
        assertEquals(byName.get().getName(), restaurantUpdateBindingModel.getName());
    }

    @Test
    void testGetRestaurantBindingModelDetailsByRestaurantIdThrowObjectNotFoundExc(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.getRestaurantBindingModelDetailsByRestaurantId(100L));
    }

    @Test
    void testGetAllRestaurants(){
        List<RestaurantViewDetails> allRestaurants = restaurantService.getAllRestaurants();
        assertEquals(1, allRestaurants.size());
        assertInstanceOf(RestaurantViewDetails.class, allRestaurants.get(0));
    }

    @Test
    void testUpdateRestaurant() throws IOException {
        Restaurant byName = restaurantRepository.findByName(restaurantName).orElseThrow();
        byte[] content =  { (byte) 204, 29, (byte) 207, (byte) 217 };
        RestaurantUpdateBindingModel restaurantUpdateBindingModel =
                new RestaurantUpdateBindingModel().setName("newRestaurant")
                        .setActive(byName.getActive())
                        .setLat("100")
                        .setLon("200");
                restaurantUpdateBindingModel
                        .setFile(new MockMultipartFile("image.jpeg", "originalName.jpeg", "type", content));

        Restaurant restaurant = restaurantService.updateRestaurant(restaurantUpdateBindingModel, byName.getId());

        assertEquals(restaurantUpdateBindingModel.getName(), restaurant.getName());
        assertEquals(Double.parseDouble(restaurantUpdateBindingModel.getLon()), restaurant.getLon());
        assertEquals(Double.parseDouble(restaurantUpdateBindingModel.getLat()), restaurant.getLat());
    }

    @Test
    void testUpdateRestaurantThrowObjectNotFound() throws IOException {
        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.updateRestaurant(new RestaurantUpdateBindingModel(), 100L));
    }

    @Test
    void testDeleteThrowObjectNotFound() throws IOException {
        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.delete(100L));

    }

    @Test
    void testUpdateRestaurantsWithTableThrowObjectNotFound(){
        //Restaurant byName = restaurantRepository.findByName(restaurantName).orElseThrow();
        TableCreateBindingModel tableBindingModel =
                new TableCreateBindingModel().setName("T1").setRestaurantId(100L);

        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.updateRestaurantsWithTable(tableBindingModel));

       // Restaurant updatedRestaurant = restaurantRepository.findById(1L).orElseThrow();
        //List<Restaurant> all = restaurantRepository.findAll();
        //List<TableEntity> all1 = tableRepository.findAll();
    }

    @Test
    void testGetRestaurantName(){
        Restaurant byName = restaurantRepository.findByName(restaurantName).orElseThrow();

        String restaurantName1 = restaurantService.getRestaurantName(byName.getId());

        assertEquals(byName.getName(), restaurantName1);
    }

    @Test
    void testGetRestaurantNameThrowObjectNotFound(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.getRestaurantName(100L));
    }

    @Test
    void testGetCoordinates(){
        Restaurant byName = restaurantRepository.findByName(restaurantName).orElseThrow();

        List<Double> coordinates = restaurantService.getCoordinates(byName.getId());

        assertEquals(2, coordinates.size());
        assertInstanceOf(Double.class, coordinates.get(0));
        assertEquals(byName.getLon(), coordinates.get(0));
        assertInstanceOf(Double.class, coordinates.get(1));
        assertEquals(byName.getLat(), coordinates.get(1));
    }
    @Test
    void testGetCoordinatesThrowObjectNotFound(){
        assertThrows(
                ObjectNotFoundException.class,
                () -> restaurantService.getCoordinates(100L));
    }
}
