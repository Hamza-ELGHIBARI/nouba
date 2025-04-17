package com.hamza.nouba.controllers;

import com.hamza.nouba.entities.Client;
import com.hamza.nouba.entities.Ticket;
import com.hamza.nouba.entities.User;
import com.hamza.nouba.repositories.ClientRepository;
import com.hamza.nouba.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final ClientRepository clientRepository;

        @PostMapping("/agency/{agencyId}")
        public Ticket takeTicket(@PathVariable Long agencyId,
                                 @AuthenticationPrincipal User user) {
            Client client = clientRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            return ticketService.generateTicket(agencyId, client);
        }

    @GetMapping("/{ticketId}/ahead")
    public int getPeopleAhead(@PathVariable Long ticketId) {
        return ticketService.getPeopleAhead(ticketId);
    }

    @PutMapping("/agency/{agencyId}/serve")
    public Ticket serveNextClient(@PathVariable Long agencyId) {
        return ticketService.serveNextClient(agencyId);
    }



}
