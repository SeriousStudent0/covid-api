package org.polytech.covidapi.Web;

import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Domain.RendezVous;
import org.polytech.covidapi.Services.DoctorServices;
import org.polytech.covidapi.Services.PatientServices;
import org.polytech.covidapi.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rendezvous")
public class RendezVousController {
    private final RendezVousService rendezVousService;
    
    @Autowired
    public RendezVousController(RendezVousService rendezVousService){
        this.rendezVousService = rendezVousService;
    }
    
    @PostMapping(path = "/create")
    public ResponseEntity<RendezVous> createRDV(@RequestBody RendezVous rdv){
        rdv.setValidated(false);
        RendezVous createdRDV= rendezVousService.createRDV(rdv);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRDV);
    }
}
