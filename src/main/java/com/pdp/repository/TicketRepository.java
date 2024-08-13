package com.pdp.repository;

import com.pdp.dto.TicketDTO;
import com.pdp.model.Ticket;
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
public class TicketRepository implements BaseRepository<Ticket, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(Ticket ticket) {
        String sql = "INSERT INTO Ticket(user_id, movie_id, cinema_hall_id, status, show_time, price, payment_method,row_seat,column_seat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticket.getUserID());
            ps.setInt(2, ticket.getMovieID());
            ps.setInt(3, ticket.getCinema_hall_id());
            ps.setString(4, ticket.getStatus());
            ps.setTimestamp(5, Timestamp.valueOf(ticket.getShowTime()));
            ps.setDouble(6, ticket.getPrice());
            ps.setString(7, ticket.getPaymentMethod());
            ps.setInt(8, ticket.getRowSeat());
            ps.setInt(9, ticket.getColumnSeat());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        return generatedId != null ? generatedId.intValue() : null;
    }

    public List<TicketDTO> getUserTickets(Integer userID) {
        return jdbcTemplate.query("SELECT * FROM UserTicketInfo WHERE user_id = ?",
                new Object[]{userID},
                new UserTicketMapper());
    }


    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM Ticket WHERE id = ?", id);
    }

    @Override
    public Ticket findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Ticket WHERE id = ?", new Object[]{id}, new TicketRowMapper());
    }

    @Override
    public List<Ticket> findAll() {
        return jdbcTemplate.query("SELECT * FROM Ticket", new TicketRowMapper());
    }

    private static class UserTicketMapper implements RowMapper<TicketDTO> {
        @Override
        public TicketDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TicketDTO(
                    rs.getTimestamp("show_time").toLocalDateTime(),
                    rs.getDouble("price"),
                    rs.getString("status"),
                    rs.getInt("row_seat"),
                    rs.getInt("column_seat"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("generated_name"),
                    rs.getString("extension")
            );
        }
    }

    private static class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Ticket.builder()
                    .id(rs.getInt("id"))
                    .userID(rs.getInt("user_id"))
                    .movieID(rs.getInt("movie_id"))
                    .cinema_hall_id(rs.getInt("cinema_hall_id"))
                    .status(rs.getString("status"))
                    .showTime(rs.getTimestamp("show_time").toLocalDateTime())
                    .rowSeat(rs.getInt("row_seat"))
                    .columnSeat(rs.getInt("column_seat"))
                    .price(rs.getDouble("price"))
                    .paymentMethod(rs.getString("payment_method"))
                    .purchaseTime(rs.getTimestamp("purchase_time").toLocalDateTime())
                    .build();
        }
    }
}
