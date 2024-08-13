package com.pdp.service;

import com.pdp.dto.CinemaDetailsDTO;
import com.pdp.dto.CinemaHallDTO;
import com.pdp.model.CinemaHall;
import com.pdp.repository.CinemaHallRepository;
import com.pdp.service.base.BaseService;
import com.pdp.service.base.DTOSavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:45
 **/
@Service
public class CinemaHallService implements BaseService<CinemaHall, Integer>, DTOSavingService<CinemaHallDTO, Integer> {

    private final CinemaHallRepository repository;

    public CinemaHallService(CinemaHallRepository cinemaHallRepository) {
        this.repository = cinemaHallRepository;
    }

    public List<CinemaDetailsDTO> findAllCinemaDetails() {
        return repository.findAllCinemaDetails();
    }

    @Override
    public Integer saveFromDTO(CinemaHallDTO dto) {
        return repository.saveFromDTO(dto);
    }

    @Override
    public Integer save(CinemaHall cinemaHall) {
        return repository.save(cinemaHall);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public CinemaHall findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<CinemaHall> findAll() {
        return repository.findAll();
    }
}
