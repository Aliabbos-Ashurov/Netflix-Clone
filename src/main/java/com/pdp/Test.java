package com.pdp;


import com.pdp.dto.LoginDTO;
import com.pdp.dto.MovieDetailsDTO;
import com.pdp.model.RolePermissions;
import com.pdp.repository.*;
import com.pdp.service.CategoryService;
import com.pdp.service.MovieCategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.Map;

/**
 * @author Aliabbos Ashurov
 * @since 04/August/2024  15:53
 **/
public class Test {

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/netflix");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("abbos2004mysql");
        MovieRepository movieRepository = new MovieRepository(new JdbcTemplate(dataSource));
        Map<String, List<MovieDetailsDTO>> moviesGroupedByCategory = movieRepository.getMoviesGroupedByCategory();

        for (Map.Entry<String, List<MovieDetailsDTO>> entry : moviesGroupedByCategory.entrySet()) {
            for (MovieDetailsDTO movieDetailsDTO : entry.getValue()) {
                System.out.println(entry.getKey());
                System.out.println(movieDetailsDTO.movieTitle());
            }
        }
    }
}
