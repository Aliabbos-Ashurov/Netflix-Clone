package com.pdp.service;

import com.pdp.dto.MovieDetailsDTO;
import com.pdp.model.Movie;
import com.pdp.repository.MovieRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:48
 **/
@Service
public class MovieService implements BaseService<Movie, Integer> {

    private final MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Map<String, List<MovieDetailsDTO>> getMoviesGroupedByCategory() {
        return repository.getMoviesGroupedByCategory();
    }

    public List<MovieDetailsDTO> findAllMovieDetailsByCategory(String category) {
        return repository.findAllMovieDetailsByCategory(category);
    }

    public List<MovieDetailsDTO> findAllMovieDetails() {
        return repository.findAllMovieDetails();
    }

    public MovieDetailsDTO findMovieDetailsById(Integer movieId) {
        return repository.findMovieDetailsById(movieId);
    }

    @Override
    public Integer save(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Movie findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }
}
