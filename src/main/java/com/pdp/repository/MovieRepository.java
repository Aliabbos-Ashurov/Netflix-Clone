package com.pdp.repository;

import com.pdp.dto.MovieDetailsDTO;
import com.pdp.model.Movie;
import com.pdp.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository implements BaseRepository<Movie, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MovieDetailsDTO> findAllMovieDetails() {
        return jdbcTemplate.query(
                "SELECT * FROM MovieDetails",
                new MovieDetailsRowMapper()
        );
    }

    public MovieDetailsDTO findMovieDetailsById(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM MovieDetails WHERE movie_id = ?",
                new Object[]{id},
                new MovieDetailsRowMapper()
        );
    }

    public Map<String, List<MovieDetailsDTO>> getMoviesGroupedByCategory() {
        Map<String, List<MovieDetailsDTO>> moviesGroupedByCategory = new HashMap<>();
        List<MovieDetailsDTO> allMovieDetails = findAllMovieDetails();
        for (MovieDetailsDTO movieDetailsDTO : allMovieDetails) {
            for (String category : movieDetailsDTO.categories()) {
                moviesGroupedByCategory
                        .computeIfAbsent(category, k -> new ArrayList<>())
                        .add(movieDetailsDTO);
            }
        }

        return moviesGroupedByCategory;
    }

    public List<MovieDetailsDTO> findAllMovieDetailsByCategory(String category) {
        String sql = "SELECT * FROM MovieDetails WHERE categories LIKE ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{"%" + category + "%"},
                new MovieDetailsRowMapper()
        );
    }

    @Override
    public Integer save(Movie movie) {
        String sql = "INSERT INTO Movie(title, director, duration, rating, language, image_id, release_date,description,thriller_link) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirector());
            ps.setInt(3, movie.getDuration());
            ps.setDouble(4, movie.getRating());
            ps.setString(5, movie.getLanguage());
            ps.setInt(6, movie.getImageID());
            ps.setDate(7, Date.valueOf(movie.getReleaseDate()));
            ps.setString(8, movie.getDescription());
            ps.setString(9, movie.getThrillerLink());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("UPDATE Movie SET is_deleted = true WHERE id = ?", id);
    }

    @Override
    public Movie findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Movie WHERE id = ?", new Object[]{id}, new MovieRowMapper());
    }

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT * FROM Movie", new MovieRowMapper());
    }

    private static class MovieRowMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Movie.builder()
                    .id(rs.getInt("id"))
                    .title(rs.getString("title"))
                    .director(rs.getString("director"))
                    .duration(rs.getInt("duration"))
                    .rating(rs.getDouble("rating"))
                    .language(rs.getString("language"))
                    .imageID(rs.getInt("image_id"))
                    .description(rs.getString("description"))
                    .releaseDate(rs.getDate("release_date").toLocalDate())
                    .thrillerLink(rs.getString("thriller_link"))
                    .build();
        }
    }

    private static class MovieDetailsRowMapper implements RowMapper<MovieDetailsDTO> {
        @Override
        public MovieDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MovieDetailsDTO(
                    rs.getInt("movie_id"),
                    rs.getString("movie_title"),
                    rs.getString("movie_director"),
                    rs.getInt("movie_duration"),
                    rs.getDouble("movie_rating"),
                    rs.getString("movie_language"),
                    rs.getString("movie_description"),
                    rs.getString("movie_thriller_link"),
                    rs.getDate("movie_release_date").toLocalDate(),
                    rs.getString("movie_image_generated_name"),
                    List.of(rs.getString("categories").split(", ")),
                    List.of(rs.getString("scene_images").split(", "))
            );
        }
    }
}
