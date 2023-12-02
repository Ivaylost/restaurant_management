package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.service.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void saveImageIntoFileSystem(MultipartFile file, String imageUrl) {
        try {
            saveImage(imageUrl, file);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    @Override
    public void delete(Restaurant restaurant) throws IOException {
        String[] split = restaurant.getImgUrl().split("\\.");
        String suffix = split[split.length-1];
        String path = ("src\\main\\resources\\static" + restaurant.getImgUrl()).replace("\\primaryImage." + suffix, "");
        File file = new File(path);
        FileUtils.cleanDirectory(file);
        FileUtils.forceDelete(file);
    }

    private void saveImage(String pathToSaveImage, MultipartFile image) throws IOException {
        File file = new File(pathToSaveImage);
        boolean mkdirs = file.getParentFile().mkdirs();
        boolean newFile = file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(image.getBytes());
        outputStream.close();
    }
}
