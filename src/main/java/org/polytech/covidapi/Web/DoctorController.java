package org.polytech.covidapi.Web;

import java.util.List;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Services.DoctorServices;
import org.polytech.covidapi.Services.PatientServices;
import org.polytech.covidapi.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import liquibase.pro.packaged.lo;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorServices doctorService;
    private final PatientServices patientService;
    
    @Autowired
    public DoctorController(DoctorServices doctorService, PatientServices patientService){
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor){
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @GetMapping(path = "/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getPatientsForDoctorId(@PathVariable Integer doctorId){
        List<Patient> patients = patientService.getPatientsByDoctorId(doctorId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping(path = "/{doctorId}/patients/search")
    public List<Patient> getAllPatientByName(
        @RequestParam(name = "name", required = true) String name){
            return patientService.getAllPatientByName(name);
    }

    @PostMapping(path = "/logging")
    public ResponseEntity<Integer> logAs(@RequestBody LoginRequest loginRequest){

        String login = loginRequest.getLogin();
        String password = loginRequest.getPassword();
        
        Integer userId = doctorService.authenticate(login, password);

        if (userId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
