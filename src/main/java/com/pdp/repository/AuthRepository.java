package com.pdp.repository;

import com.pdp.dto.LoginDTO;
import com.pdp.dto.SignUpDTO;
import com.pdp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:31
 **/
@Repository
public class AuthRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void blockUser(Integer id) {
        jdbcTemplate.update("update Users set is_active = false where id = ?", id);
    }

    public Users findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new UsersRowMapper());
    }

    public Users login(LoginDTO dto) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[]{dto.username(), dto.password()}, new UsersRowMapper());
    }

    public Users signUp(SignUpDTO dto) {
        String sql = "INSERT INTO users (fullname,username, password, email) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, dto.fullname(), dto.username(), dto.password(), dto.email());
        return rows > 0 ? Users.builder()
                .fullname(dto.fullname())
                .username(dto.username())
                .password(dto.password())
                .email(dto.email())
                .build() : null;
    }

    private static class UsersRowMapper implements RowMapper<Users> {
        @Override
        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Users.builder()
                    .id(rs.getInt("id"))
                    .fullname(rs.getString("fullname"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .build();
        }
    }
}
