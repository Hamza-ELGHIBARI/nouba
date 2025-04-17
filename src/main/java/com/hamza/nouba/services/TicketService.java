package com.hamza.nouba.services;

import com.hamza.nouba.entities.Agency;
import com.hamza.nouba.entities.Client;
import com.hamza.nouba.entities.Ticket;
import com.hamza.nouba.repositories.AgencyRepository;
import com.hamza.nouba.repositories.CityRepository;
import com.hamza.nouba.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {

    private final AgencyRepository agencyRepository;
    private final CityRepository cityRepository;
    private  final TicketRepository ticketRepository;

    public TicketService(AgencyRepository agencyRepository, CityRepository cityRepository, TicketRepository ticketRepository) {
        this.agencyRepository = agencyRepository;
        this.cityRepository = cityRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket generateTicket(Long agencyId, Client client) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new RuntimeException("Agency not found"));

        Integer lastNumber = ticketRepository.findMaxNumberByAgencyAndUnserved(agencyId).orElse(0);

        Ticket ticket = new Ticket();
        ticket.setAgency(agency);
        ticket.setClient(client);
        ticket.setNumber(lastNumber + 1);
        ticket.setIssuedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public int getPeopleAhead(Long ticketId) {
        Ticket myTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return ticketRepository.countByAgencyIdAndNumberLessThanAndServedFalse(
                myTicket.getAgency().getId(), myTicket.getNumber()
        );
    }

    public Ticket serveNextClient(Long agencyId) {
        // Get the next unserved ticket by lowest number
        Optional<Ticket> nextTicketOpt = ticketRepository.findTopByAgencyIdAndServedFalseOrderByNumberAsc(agencyId);

        Ticket ticket = nextTicketOpt.orElseThrow(() ->
                new RuntimeException("No clients in the queue"));

        ticket.setServed(true);
        ticket.setServedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }


}
