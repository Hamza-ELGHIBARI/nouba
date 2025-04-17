package com.hamza.nouba.repositories;


import com.hamza.nouba.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
