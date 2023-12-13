package ch.mse.image_archive.repository;

import org.springframework.data.repository.CrudRepository;

import ch.mse.image_archive.model.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {

}
