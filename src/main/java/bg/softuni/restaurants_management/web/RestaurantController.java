package bg.softuni.restaurants_management.web;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.dto.UploadImgDto;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.service.ImageService;
import bg.softuni.restaurants_management.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ImageService imageService;

    public RestaurantController(RestaurantService restaurantService, ImageService imageService) {
        this.restaurantService = restaurantService;
        this.imageService = imageService;
    }

    @GetMapping("/create")
    public ModelAndView create(
            @ModelAttribute("restaurantCreateBindingModel") RestaurantCreateBindingModel restaurantCreateBindingModel
    ) {
        return new ModelAndView("restaurant-create");
    }

    @PostMapping("/create")
    public ModelAndView register(@ModelAttribute("restaurantCreateBindingModel") @Valid RestaurantCreateBindingModel restaurantCreateBindingModel,
                                 BindingResult bindingResult) {

        boolean success = imageService.save(restaurantCreateBindingModel);

        Restaurant restaurant = restaurantCreateBindingModel.mapToEntity();

        if (bindingResult.hasErrors()) {
            return new ModelAndView("restaurant-create");
        }

        boolean hasSuccessfulRegistration = restaurantService.createRestaurant(restaurantCreateBindingModel);

        if (!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("restaurant-create");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/create");
    }

    @PostMapping("/saveImg")
    public ModelAndView saveSpecimen(@RequestParam("imageFile") MultipartFile imageFile) {
        ModelAndView modelAndView = new ModelAndView();
//        try {
//            restaurantService.save(specimenDTO);
//        } catch (Exception e) {
//            System.out.println();
//            return modelAndView;
//        }


        UploadImgDto imgDto = new UploadImgDto();
        imgDto.setFileName(imageFile.getOriginalFilename());
        imgDto.setPath("/img/");
        try {
            restaurantService.saveImage(imageFile, imgDto);
        } catch (Exception e) {
            System.out.println("------------------" + e);
            return modelAndView;
        }
//
//        modelAndView.addObject("photoDTO", photoDTO);
//        modelAndView.addObject("specimenDTO", specimenDTO);
        return modelAndView;

    }
}
