package org.polytech.covidapi.Web;

import java.util.List;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Services.HealthCenterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("public/healthcenter")
public class HealthCenterController {
    private final HealthCenterServices healthcenterService;

    @Autowired
    public HealthCenterController(HealthCenterServices healthcenterService){
        this.healthcenterService = healthcenterService;
    }
    
    @PostMapping(path = "/create")
    public ResponseEntity<HealthCenter> createHealthCenter(@RequestBody HealthCenter healthCenter){
        HealthCenter createdHealthCenter = healthcenterService.createHealthCenter(healthCenter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHealthCenter);
    }

    @GetMapping
    public List<HealthCenter> getAllHealthCenterList(){
        return healthcenterService.getAll();
    }

    @GetMapping(path = "/{id}/doctors")
    public ResponseEntity<List<Doctor>> getCenterDoctorList(@PathVariable Integer id){
        List<Doctor> doctors = healthcenterService.getCenterDoctorList(id);
        return ResponseEntity.ok(doctors);
    }
}
