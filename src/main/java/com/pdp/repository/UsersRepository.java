package com.pdp.repository;

import com.pdp.dto.SignUpDTO;
import com.pdp.model.Users;
import com.pdp.repository.base.BaseRepository;
import com.pdp.repository.base.DTOSavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class UsersRepository implements BaseRepository<Users, Integer>, DTOSavingRepository<SignUpDTO, Integer> {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Integer saveFromDTO(SignUpDTO dto) {
        String sql = "INSERT INTO Users(fullname, username, password, email) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.fullname());
            ps.setString(2, dto.username());
            ps.setString(3, passwordEncoder.encode(dto.password()));
            ps.setString(4, dto.email());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public Integer save(Users user) {
        String sql = "INSERT INTO Users(fullname, username, password, email) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getUsername());
            ps.setString(3, passwordEncoder.encode(user.getPassword()));
            ps.setString(4, user.getEmail());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Users WHERE id = ?", id);
    }

    @Override
    public Users findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Users WHERE id = ?", new Object[]{id}, new UsersRowMapper());
    }

    @Override
    public List<Users> findAll() {
        return jdbcTemplate.query("SELECT * FROM Users WHERE is_active = true", new UsersRowMapper());
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
                    .phoneNumber(rs.getString("phone_number"))
                    .imageID(rs.getInt("image_id"))
                    .isActive(rs.getBoolean("is_active"))
                    .build();
        }
    }
}
