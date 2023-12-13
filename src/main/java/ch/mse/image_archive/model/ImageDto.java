package ch.mse.image_archive.model;

public class ImageDto {

    public int id;
    public String name;
    public String imagePath;

    public ImageDto(Image image) {
        this.id = image.getId();
        this.name = image.getName();
        this.imagePath = "images/" + image.getId();
    }
}
