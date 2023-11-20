package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String save(RestaurantCreateBindingModel restaurantCreateBindingModel) {

        MultipartFile image = restaurantCreateBindingModel.getFile();

        String imagePath = "src\\main\\resources\\static\\images\\" + restaurantCreateBindingModel.getName()
        +"\\primaryImage.jpg";

        try {
            File file = new File(imagePath);
            boolean mkdirs = file.getParentFile().mkdirs();
            boolean newFile = file.createNewFile();

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(image.getBytes());
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }

        return imagePath;
    }
}
