package com.pdp.service;

import com.pdp.model.Image;
import com.pdp.repository.ImageRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:46
 **/
@Service
public class ImageService implements BaseService<Image, Integer> {

    private final ImageRepository repository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.repository = imageRepository;
    }

    @Override
    public Integer save(Image image) {
        return repository.save(image);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Image findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> findAll() {
        return repository.findAll();
    }
}
