package com.pdp.repository;

import com.pdp.model.MovieSceneImage;
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
public class MovieSceneImageRepository implements BaseRepository<MovieSceneImage, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieSceneImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(MovieSceneImage movieSceneImage) {
        String sql = "INSERT INTO MovieSceneImage(movie_id, image_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, movieSceneImage.getMovieID());
            ps.setInt(2, movieSceneImage.getImageID());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM MovieSceneImage WHERE id = ?", id);
    }

    @Override
    public MovieSceneImage findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM MovieSceneImage WHERE id = ?", new Object[]{id}, new MovieSceneImageRowMapper());
    }

    @Override
    public List<MovieSceneImage> findAll() {
        return jdbcTemplate.query("SELECT * FROM MovieSceneImage", new MovieSceneImageRowMapper());
    }

    private static class MovieSceneImageRowMapper implements RowMapper<MovieSceneImage> {
        @Override
        public MovieSceneImage mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MovieSceneImage.builder()
                    .id(rs.getInt("id"))
                    .movieID(rs.getInt("movie_id"))
                    .imageID(rs.getInt("image_id"))
                    .build();
        }
    }
}
