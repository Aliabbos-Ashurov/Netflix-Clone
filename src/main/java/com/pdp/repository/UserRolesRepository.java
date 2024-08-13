package com.pdp.repository;

import com.pdp.model.Role;
import com.pdp.model.UserRoles;
import com.pdp.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  14:46
 **/
@Repository
public class UserRolesRepository implements BaseRepository<UserRoles, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRolesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Set<Role> getUserRoles(Integer userId) {
        String query = "SELECT r.id, r.name FROM Role r JOIN UserRoles ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        return new HashSet<>(jdbcTemplate.query(query, new Object[]{userId}, roleRowMapper()));
    }

    @Override
    public Integer save(UserRoles userRoles) {
        String query = "INSERT INTO UserRoles(user_id, role_id) VALUES (?, ?)";
        return jdbcTemplate.update(query, userRoles.getUserID(), userRoles.getRoleID());
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM UserRoles WHERE user_id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public UserRoles findById(Integer id) {
        String query = "SELECT * FROM UserRoles WHERE user_id = ? AND role_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, userRolesRowMapper());
    }

    @Override
    public List<UserRoles> findAll() {
        String query = "SELECT * FROM UserRoles";
        return jdbcTemplate.query(query, userRolesRowMapper());
    }

    private RowMapper<UserRoles> userRolesRowMapper() {
        return (rs, rowNum) -> UserRoles.builder()
                .roleID(rs.getInt("role_id"))
                .userID(rs.getInt("user_id"))
                .build();
    }

    private RowMapper<Role> roleRowMapper() {
        return (rs, rowNum) -> Role.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
