package org.polytech.covidapi.Web;

import java.util.List;

import java.util.Map;

import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Domain.RendezVous;
import org.polytech.covidapi.Services.DoctorServices;
import org.polytech.covidapi.Services.PatientServices;
import org.polytech.covidapi.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @PostMapping(path = "public/create")
    public ResponseEntity<RendezVous> createRDV(@RequestBody RendezVous rdv){
        rdv.setValidated(false);
        RendezVous createdRDV= rendezVousService.createRDV(rdv);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRDV);
    }

    @PutMapping(path = "private/create")
    public ResponseEntity<RendezVous> updateRDV(@RequestBody Map<String, Object> requestBody){
        Integer rdvId = (Integer) requestBody.get("rdvId");
        Integer doctorId = (Integer) requestBody.get("doctorId");

        RendezVous updatedRDV= rendezVousService.validateRDV(rdvId, doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRDV);
    }

    @GetMapping(path = "private/pending")
    public List<RendezVous> getpendingRDVList(){
        return rendezVousService.getAllNotValidated();
    }
}
