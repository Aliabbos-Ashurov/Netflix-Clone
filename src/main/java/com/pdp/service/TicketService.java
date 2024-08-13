package com.pdp.service;

import com.pdp.dto.TicketDTO;
import com.pdp.model.Ticket;
import com.pdp.repository.TicketRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:50
 **/
@Service
public class TicketService implements BaseService<Ticket, Integer> {

    private final TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public List<TicketDTO> getUserTickets(Integer userID) {
        return repository.getUserTickets(userID);
    }

    @Override
    public Integer save(Ticket ticket) {
        return repository.save(ticket);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Ticket findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return repository.findAll();
    }
}
