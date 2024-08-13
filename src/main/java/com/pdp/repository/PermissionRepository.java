package com.pdp.repository;

import com.pdp.model.Permission;
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
public class PermissionRepository implements BaseRepository<Permission, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(Permission permission) {
        String sql = "INSERT INTO Permission(name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, permission.getName());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Permission WHERE id = ?", id);
    }

    @Override
    public Permission findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Permission WHERE id = ?", new Object[]{id}, new PermissionRowMapper());
    }

    @Override
    public List<Permission> findAll() {
        return jdbcTemplate.query("SELECT * FROM Permission", new PermissionRowMapper());
    }

    private static class PermissionRowMapper implements RowMapper<Permission> {
        @Override
        public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Permission.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}
