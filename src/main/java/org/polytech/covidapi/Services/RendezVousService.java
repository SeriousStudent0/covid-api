package org.polytech.covidapi.Services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.RendezVous;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.Repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public RendezVousService(RendezVousRepository rendezVousRepository, DoctorRepository doctorRepository){
        this.rendezVousRepository = rendezVousRepository;
        this.doctorRepository = doctorRepository;
    }
    
    public RendezVous createRDV(RendezVous rdv){
        return rendezVousRepository.save(rdv);
    }

    @PreAuthorize("hasRole('USER')")
    public List<RendezVous> getAllNotValidated(){
        return rendezVousRepository.findByValidatedFalse();
    }

    @PreAuthorize("hasRole('USER')")
    public RendezVous validateRDV(Integer rdvId, Integer doctorId){
        RendezVous pointedRDV = rendezVousRepository.findById(rdvId)
            .orElseThrow(() -> new EntityNotFoundException("Rendez-vous Not Found"));
        Doctor pointedDoctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new EntityNotFoundException("Doctor Not Found"));

        pointedRDV.setValidated(true);
        pointedRDV.setDoctor(pointedDoctor);
        return rendezVousRepository.save(pointedRDV);
    } 
}
