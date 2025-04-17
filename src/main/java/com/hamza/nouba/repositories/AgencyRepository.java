package com.hamza.nouba.repositories;


import com.hamza.nouba.entities.Agency;
import com.hamza.nouba.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    List<Agency> findByCityId(Long cityId);
}
