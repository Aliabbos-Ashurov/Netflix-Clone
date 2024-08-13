package com.pdp.repository;

import com.pdp.model.MovieCategory;
import com.pdp.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class MovieCategoryRepository implements BaseRepository<MovieCategory, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(MovieCategory movieCategory) {
        String sql = "INSERT INTO MovieCategory(movie_id, category_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movieCategory.getMovieID());
            ps.setInt(2, movieCategory.getCategoryID());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM MovieCategory WHERE id = ?", id);
    }

    @Override
    public MovieCategory findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM MovieCategory WHERE id = ?", new Object[]{id}, new MovieCategoryRowMapper());
    }

    @Override
    public List<MovieCategory> findAll() {
        return jdbcTemplate.query("SELECT * FROM MovieCategory", new MovieCategoryRowMapper());
    }

    private static class MovieCategoryRowMapper implements RowMapper<MovieCategory> {
        @Override
        public MovieCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MovieCategory.builder()
                    .id(rs.getInt("id"))
                    .movieID(rs.getInt("movie_id"))
                    .categoryID(rs.getInt("category_id"))
                    .build();
        }
    }
}
