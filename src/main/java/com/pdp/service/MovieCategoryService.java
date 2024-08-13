package com.pdp.service;

import com.pdp.model.MovieCategory;
import com.pdp.repository.MovieCategoryRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:47
 **/
@Service
public class MovieCategoryService implements BaseService<MovieCategory, Integer> {

    private final MovieCategoryRepository repository;

    @Autowired
    public MovieCategoryService(MovieCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(MovieCategory movieCategory) {
        return repository.save(movieCategory);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public MovieCategory findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<MovieCategory> findAll() {
        return repository.findAll();
    }
}
