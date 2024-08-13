package com.pdp.repository;

import com.pdp.model.Image;
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
public class ImageRepository implements BaseRepository<Image, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(Image image) {
        String sql = "INSERT INTO Image(generated_name, file_name, file_type, size, extension) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, image.getGeneratedName());
            ps.setString(2, image.getFileName());
            ps.setString(3, image.getFileType());
            ps.setLong(4, image.getSize());
            ps.setString(5, image.getExtension());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Image WHERE id = ?", id);
    }

    @Override
    public Image findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Image WHERE id = ?", new Object[]{id}, new ImageRowMapper());
    }

    @Override
    public List<Image> findAll() {
        return jdbcTemplate.query("SELECT * FROM Image", new ImageRowMapper());
    }

    private static class ImageRowMapper implements RowMapper<Image> {
        @Override
        public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Image.builder()
                    .id(rs.getInt("id"))
                    .generatedName(rs.getString("generated_name"))
                    .fileName(rs.getString("file_name"))
                    .fileType(rs.getString("file_type"))
                    .size(rs.getLong("size"))
                    .extension(rs.getString("extension"))
                    .build();
        }
    }
}
