package org.polytech.covidapi.Repository;

import java.util.List;
import java.util.Optional;

import org.polytech.covidapi.Domain.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Integer>{
    
    Optional<RendezVous> findById(Integer idRDV);
    
    List<RendezVous> findAll();
    
}
