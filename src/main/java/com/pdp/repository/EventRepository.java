package com.pdp.repository;

import com.pdp.dto.EventCinemaDTO;
import com.pdp.model.Event;
import com.pdp.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class EventRepository implements BaseRepository<Event, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean hasEventConflict(int cinemaHallId, LocalDateTime eventStartTime) {
        LocalDateTime eventEndTime = eventStartTime.plusHours(3);
        Timestamp eventStartTimestamp = Timestamp.valueOf(eventStartTime);
        Timestamp eventEndTimestamp = Timestamp.valueOf(eventEndTime);
        Timestamp conflictStartTimestamp = Timestamp.valueOf(eventStartTime.minusHours(2).minusMinutes(59));
        Timestamp conflictEndTimestamp = Timestamp.valueOf(eventEndTime.plusHours(2).plusMinutes(59));

        String sql = "SELECT COUNT(*) > 0 FROM Event WHERE cinema_hall_id = ? AND (" +
                "(show_time BETWEEN ? AND ?) OR " +
                "(DATE_ADD(show_time, INTERVAL 3 HOUR) BETWEEN ? AND ?) OR " +
                "(show_time <= ? AND DATE_ADD(show_time, INTERVAL 3 HOUR) >= ?))";

        return jdbcTemplate.queryForObject(sql, Boolean.class, cinemaHallId, conflictStartTimestamp, conflictEndTimestamp, conflictStartTimestamp, conflictEndTimestamp, eventEndTimestamp, eventStartTimestamp);
    }

    public List<EventCinemaDTO> findUpcomingEventsByMovieId(int movieId) {
        String sql = "SELECT * FROM EventCinemaView " +
                "WHERE movie_id = ? AND show_time > ? " +
                "ORDER BY show_time ASC";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureTime = now.plus(5, ChronoUnit.MINUTES);

        return jdbcTemplate.query(sql, new Object[]{movieId, Timestamp.valueOf(futureTime)},
                new EventCinemaRowMapper());
    }

    @Override
    public Integer save(Event event) {
        String sql = "INSERT INTO Event(movie_id, cinema_hall_id, show_time, price, status, description,capacity) VALUES (?, ?, ?, ?, ?, ?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, event.getMovieID());
            ps.setInt(2, event.getCinemaHallID());
            ps.setTimestamp(3, Timestamp.valueOf(event.getShowTime()));
            ps.setDouble(4, event.getPrice());
            ps.setString(5, event.getStatus());
            ps.setString(6, event.getDescription());
            ps.setString(7, event.getCapacity());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Event WHERE id = ?", id);
    }

    public void update(Event event) {
        String sql = "UPDATE Event SET capacity = ? WHERE id = ?";
        jdbcTemplate.update(sql, event.getCapacity(), event.getId());
    }

    @Override
    public Event findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Event WHERE id = ?", new Object[]{id}, new EventRowMapper());
    }

    @Override
    public List<Event> findAll() {
        return jdbcTemplate.query("SELECT * FROM Event", new EventRowMapper());
    }

    private static class EventRowMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Event.builder()
                    .id(rs.getInt("id"))
                    .movieID(rs.getInt("movie_id"))
                    .cinemaHallID(rs.getInt("cinema_hall_id"))
                    .showTime(rs.getTimestamp("show_time").toLocalDateTime())
                    .price(rs.getDouble("price"))
                    .status(rs.getString("status"))
                    .capacity(rs.getString("capacity"))
                    .description(rs.getString("description"))
                    .build();
        }
    }

    private static class EventCinemaRowMapper implements RowMapper<EventCinemaDTO> {

        @Override
        public EventCinemaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new EventCinemaDTO(
                    rs.getInt("event_id"),
                    rs.getInt("movie_id"),
                    rs.getInt("cinema_hall_id"),
                    rs.getTimestamp("show_time").toLocalDateTime(),
                    rs.getDouble("price"),
                    rs.getString("capacity"),
                    rs.getString("event_status"),
                    rs.getString("event_description"),
                    rs.getString("cinema_hall_name"),
                    rs.getString("cinema_hall_capacity"),
                    rs.getString("cinema_hall_status"),
                    rs.getString("cinema_hall_facilities"),
                    rs.getInt("cinema_hall_image_id"),
                    rs.getTimestamp("cinema_hall_created_at").toLocalDateTime(),
                    rs.getString("image_generated_name"),
                    rs.getString("image_extension")
            );
        }
    }
}
