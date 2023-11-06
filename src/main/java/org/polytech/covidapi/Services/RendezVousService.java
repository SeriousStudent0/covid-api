package org.polytech.covidapi.Services;

import org.polytech.covidapi.Domain.RendezVous;
import org.polytech.covidapi.Repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;

    @Autowired
    public RendezVousService(RendezVousRepository rendezVousRepository){
        this.rendezVousRepository = rendezVousRepository;
    }
    
    public RendezVous createRDV(RendezVous rdv){
        return rendezVousRepository.save(rdv);
    }
    
}
