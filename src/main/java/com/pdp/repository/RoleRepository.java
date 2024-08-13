package com.pdp.repository;

import com.pdp.model.Role;
import com.pdp.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  14:41
 **/
@Repository
public class RoleRepository implements BaseRepository<Role, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(Role role) {
        String query = "INSERT INTO Role(name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, role.getName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM Role WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public Role findById(Integer id) {
        String query = "SELECT * FROM Role WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, roleRowMapper());
    }

    @Override
    public List<Role> findAll() {
        String query = "SELECT * FROM Role";
        return jdbcTemplate.query(query, roleRowMapper());
    }

    private RowMapper<Role> roleRowMapper() {
        return (rs, rowNum) -> Role.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
