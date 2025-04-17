package com.hamza.nouba.repositories;

import com.hamza.nouba.entities.Client;
import com.hamza.nouba.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);
}

