package bg.softuni.restaurants_management.model.dto;

public class UploadImgDto {
    private String path;
    private String fileName;

    public String getPath() {
        return path;
    }

    public UploadImgDto setPath(String path) {
        this.path = path;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public UploadImgDto setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
