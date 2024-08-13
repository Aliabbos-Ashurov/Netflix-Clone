package com.pdp.service;

import com.pdp.model.Category;
import com.pdp.repository.CategoryRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:44
 **/
@Service
public class CategoryService implements BaseService<Category, Integer> {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(Category category) {
        return repository.save(category);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Category findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }
}
