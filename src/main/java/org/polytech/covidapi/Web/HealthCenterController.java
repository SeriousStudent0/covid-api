package org.polytech.covidapi.Web;

import java.util.List;
import java.util.Map;

import org.polytech.covidapi.Domain.Address;
import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Services.AddressService;
import org.polytech.covidapi.Services.HealthCenterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("public/healthcenter")
public class HealthCenterController {
    private final HealthCenterServices healthcenterService;
    private final AddressService addressService;

    @Autowired
    public HealthCenterController(HealthCenterServices healthcenterService, AddressService addressService){
        this.healthcenterService = healthcenterService;
        this.addressService = addressService;
    }
    
    @PostMapping(path = "/create")
    public ResponseEntity<HealthCenter> createHealthCenter(@RequestBody Map<String, Object> request){
        String centerName = (String) request.get("centerName");
        Integer addressId = (Integer) request.get("addressId");

        if (centerName == null || addressId == null) {
            // Handle the case where the required parameters are missing
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        HealthCenter healthCenter = new HealthCenter();
        healthCenter.setName(centerName);

        Address address = addressService.getById(addressId).orElse(null);

        if (address == null) {
            // Handle the case where the provided addressId is invalid or not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        healthCenter.setAddress(address);

        HealthCenter createdHealthCenter = healthcenterService.createHealthCenter(healthCenter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHealthCenter);
    }

    @PutMapping(path = "/create")
    public ResponseEntity<HealthCenter> updateHealthCenter(@RequestBody HealthCenter healthCenter){

        if (healthCenter == null) {
            // Handle the case where the required parameters are missing
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        HealthCenter updatedCenter = healthcenterService.updateHealthCenter(healthCenter);
        if (updatedCenter != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedCenter);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
