package com.pdp.repository;

import com.pdp.dto.CinemaDetailsDTO;
import com.pdp.dto.CinemaHallDTO;
import com.pdp.model.CinemaHall;
import com.pdp.repository.base.BaseRepository;
import com.pdp.repository.base.DTOSavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CinemaHallRepository implements BaseRepository<CinemaHall, Integer>, DTOSavingRepository<CinemaHallDTO, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CinemaHallRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer saveFromDTO(CinemaHallDTO dto) {
        String sql = "INSERT INTO CinemaHall(name, capacity, status, opening_hours, closing_hours, facilities, image_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.name());
            ps.setString(2, dto.capacity());
            ps.setString(3, dto.status());
            ps.setTime(4, Time.valueOf(dto.openingHours()));
            ps.setTime(5, Time.valueOf(dto.closingHours()));
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public Integer save(CinemaHall object) {
        String sql = "INSERT INTO CinemaHall(name, capacity, status, opening_hours, closing_hours, facilities, image_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, object.getName());
            ps.setString(2, object.getCapacity());
            ps.setString(3, object.getStatus());
            ps.setTime(4, Time.valueOf(object.getOpeningTime()));
            ps.setTime(5, Time.valueOf(object.getClosingTime()));
            ps.setString(6, object.getFacilities());
            ps.setInt(7, object.getImageID());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM CinemaHall WHERE id = ?", id);
    }

    @Override
    public CinemaHall findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM CinemaHall WHERE id = ?", new Object[]{id}, new CinemaHallRowMapper());
    }

    @Override
    public List<CinemaHall> findAll() {
        return jdbcTemplate.query("SELECT * FROM CinemaHall", new CinemaHallRowMapper());
    }

    public List<CinemaDetailsDTO> findAllCinemaDetails() {
        String sql = "SELECT * FROM CinemaDetails";
        return jdbcTemplate.query(sql, new CinemaDetailsRowMapper());
    }

    private static class CinemaHallRowMapper implements RowMapper<CinemaHall> {
        @Override
        public CinemaHall mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CinemaHall.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .capacity(rs.getString("capacity"))
                    .status(rs.getString("status"))
                    .openingTime(rs.getTime("opening_hours").toLocalTime())
                    .closingTime(rs.getTime("closing_hours").toLocalTime())
                    .facilities(rs.getString("facilities"))
                    .imageID(rs.getInt("image_id"))
                    .build();
        }
    }

    private static class CinemaDetailsRowMapper implements RowMapper<CinemaDetailsDTO> {
        @Override
        public CinemaDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CinemaDetailsDTO(
                    rs.getInt("cinema_hall_id"),
                    rs.getString("cinema_hall_name"),
                    rs.getString("capacity"),
                    rs.getString("cinema_hall_status"),
                    rs.getTime("opening_hours"),
                    rs.getTime("closing_hours"),
                    rs.getString("facilities"),
                    rs.getTimestamp("cinema_created_at"),
                    rs.getObject("image_id", Integer.class),
                    rs.getString("image_generated_name"),
                    rs.getString("image_extension"),
                    rs.getTimestamp("image_created_at")
            );
        }
    }
}
