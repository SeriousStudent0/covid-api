package org.polytech.covidapi.Repository;

import java.util.List;

import org.polytech.covidapi.Domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenter, Integer>{

    List<HealthCenter> findAll();
    HealthCenter create();
}
