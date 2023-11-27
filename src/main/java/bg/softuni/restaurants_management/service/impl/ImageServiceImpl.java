package bg.softuni.restaurants_management.service.impl;

import bg.softuni.restaurants_management.model.dto.RestaurantCreateBindingModel;
import bg.softuni.restaurants_management.model.entity.Restaurant;
import bg.softuni.restaurants_management.repository.RestaurantRepository;
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


//    @Override
//    public String saveImageIntoFileSystem(RestaurantCreateBindingModel restaurantCreateBindingModel) {
//
//        MultipartFile image = restaurantCreateBindingModel.getFile();
//        String[] suffix = image.getOriginalFilename().split("\\.");
//
//        String pathToSaveImage = "src\\main\\resources\\static\\images\\" + restaurantCreateBindingModel.getName()
//                + "\\primaryImage." + suffix[1];
//
//        String imagePath = "\\images\\" + restaurantCreateBindingModel.getName()
//                + "\\primaryImage." + suffix[1];
//
//        try {
//            saveImage(pathToSaveImage, image);
//            return imagePath;
//        } catch (IOException e) {
//            System.out.println(e.getStackTrace());
//            return null;
//        }
//    }
//
//    @Override
//    public String updateImageIntoFileSystem(RestaurantUpdateBindingModel restaurantUpdateBindingModel) throws IOException {
//
//        Optional<Restaurant> oldRestaurant = restaurantRepository.findById(restaurantUpdateBindingModel.getId());
//
//        if (oldRestaurant.isPresent()) {
//
//            String pathToSaveImage = "src\\main\\resources\\static\\images\\" + restaurantUpdateBindingModel.getName()
//                    + "\\primaryImage.";
//            String imagePath = "\\images\\" + restaurantUpdateBindingModel.getName()
//                    + "\\primaryImage.";
//            String oldPathToSaveImage = "src\\main\\resources\\static" + oldRestaurant.get().getImgUrl();
//            String p = "src\\main\\resources\\static\\images\\" + restaurantUpdateBindingModel.getName();
//
//            if (!oldRestaurant.get().getName().equals(restaurantUpdateBindingModel.getName())
//                    && restaurantUpdateBindingModel.getFile().getSize() == 0) {
//                String suffix = oldPathToSaveImage.substring(oldPathToSaveImage.lastIndexOf('.') + 1);
//                boolean isMoved = updateImage(oldPathToSaveImage, pathToSaveImage
//                        + suffix);
//                return isMoved ? imagePath + suffix : null;
//            }
//
//            if (!oldRestaurant.get().getName().equals(restaurantUpdateBindingModel.getName())
//                    && restaurantUpdateBindingModel.getFile().getSize() > 0) {
//                String[] suffix = restaurantUpdateBindingModel.getFile().getOriginalFilename().split("\\.");
//                String path = pathToSaveImage + suffix[1];
//                boolean isUpdated = updateImage(oldPathToSaveImage, path);
//                try {
//                    saveImage(path, restaurantUpdateBindingModel.getFile());
//                    return imagePath + suffix[1];
//                } catch (IOException e) {
//                    System.out.println(e.getStackTrace());
//                    return null;
//                }
//            }
//
//            if (restaurantUpdateBindingModel.getFile().getSize() > 0) {
//                MultipartFile image = restaurantUpdateBindingModel.getFile();
//                String[] suffix = image.getOriginalFilename().split("\\.");
//                try {
//                    saveImage(pathToSaveImage + suffix[1], restaurantUpdateBindingModel.getFile());
//                    return imagePath + suffix[1];
//                } catch (IOException e) {
//                    System.out.println(e.getStackTrace());
//                    return null;
//                }
//            }
//
//        }
//        return null;
//    }
//
//    private boolean updateImage(String oldPathToSaveImage, String pathToSaveImage) throws IOException {
//        File fileToMove = new File(oldPathToSaveImage);
//        File newFile = new File("src\\main\\resources\\static\\images\\Brothers Restaurant - Dupnica1");
//
////        boolean mkdirs = newFile.getParentFile().mkdirs();
//        boolean mkdirs = newFile.mkdirs();
////        OutputStream outputStream = new FileOutputStream(newFile);
////        outputStream.close();
//
//        boolean isMoved = fileToMove.renameTo(new File(pathToSaveImage));
//        OutputStream outputStream1 = new FileOutputStream(fileToMove);
//        outputStream1.close();
//
//        if (isMoved) {
////            boolean delete = fileToMove.delete();
//            FileUtils.forceDelete(fileToMove);
//        }
//        return isMoved;
//    }
//
//    private void saveImage(String pathToSaveImage, MultipartFile image) throws IOException {
//        File file = new File(pathToSaveImage);
//        boolean mkdirs = file.getParentFile().mkdirs();
//        boolean newFile = file.createNewFile();
//
//        OutputStream outputStream = new FileOutputStream(file);
//        outputStream.write(image.getBytes());
//        outputStream.close();
//    }
}
