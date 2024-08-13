package com.pdp.repository;

import com.pdp.model.Permission;
import com.pdp.model.RolePermissions;
import com.pdp.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RolePermissionsRepository implements BaseRepository<RolePermissions, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolePermissionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Set<Permission> getRolePermissions(Integer roleId) {
        String query = "SELECT p.id, p.name FROM Permission p JOIN RolePermissions rp ON p.id = rp.permission_id WHERE rp.role_id = ?";

        return new HashSet<>(jdbcTemplate.query(query, new Object[]{roleId}, new PermissionRowMapper()));
    }

    @Override
    public Integer save(RolePermissions rolePermissions) {
        String sql = "INSERT INTO RolePermissions(role_id, permission_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rolePermissions.getRoleID());
            ps.setInt(2, rolePermissions.getPermissionID());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM RolePermissions WHERE id = ?", id);
    }

    @Override
    public RolePermissions findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM RolePermissions WHERE id = ?", new Object[]{id}, new UserPermissionsRowMapper());
    }

    @Override
    public List<RolePermissions> findAll() {
        return jdbcTemplate.query("SELECT * FROM RolePermissions", new UserPermissionsRowMapper());
    }

    private static class UserPermissionsRowMapper implements RowMapper<RolePermissions> {
        @Override
        public RolePermissions mapRow(ResultSet rs, int rowNum) throws SQLException {
            return RolePermissions.builder()
                    .id(rs.getInt("id"))
                    .roleID(rs.getInt("role_id"))
                    .permissionID(rs.getInt("permission_id"))
                    .build();
        }
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
