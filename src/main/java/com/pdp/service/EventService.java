package com.pdp.service;

import com.pdp.dto.EventCinemaDTO;
import com.pdp.model.Event;
import com.pdp.repository.EventRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:46
 **/
@Service
public class EventService implements BaseService<Event, Integer> {

    private final EventRepository repository;

    @Autowired
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(Event event) {
        return repository.save(event);
    }

    public List<EventCinemaDTO> findUpcomingEventByMovieId(int movieId) {
        return repository.findUpcomingEventsByMovieId(movieId);
    }

    public boolean hasEventConflict(int cinemaHallId, LocalDateTime eventStartTime) {
        return repository.hasEventConflict(cinemaHallId, eventStartTime);
    }
    public void update(Event event) {
        repository.update(event);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Event findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }
}
