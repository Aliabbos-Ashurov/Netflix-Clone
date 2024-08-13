package com.pdp.service;

import com.pdp.model.MovieSceneImage;
import com.pdp.repository.MovieSceneImageRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:49
 **/
@Service
public class MovieSceneImageService implements BaseService<MovieSceneImage, Integer> {


    private final MovieSceneImageRepository repository;

    @Autowired
    public MovieSceneImageService(MovieSceneImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(MovieSceneImage movieSceneImage) {
        return repository.save(movieSceneImage);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public MovieSceneImage findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<MovieSceneImage> findAll() {
        return repository.findAll();
    }
}
