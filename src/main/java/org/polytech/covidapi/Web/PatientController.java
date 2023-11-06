package org.polytech.covidapi.Web;

import java.util.List;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Services.PatientServices;
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
@RequestMapping("/patient")
public class PatientController {

    private final PatientServices patientService;

    @Autowired
    private PatientController (PatientServices patientService){
        this.patientService = patientService;
    }

    @GetMapping(path = "private/{doctorId}/patients")
    public List<Patient> getAllPatientForDoctorList(@PathVariable Integer doctorId){
        return patientService.getPatientsByDoctorId(doctorId);
    }

    @PostMapping(path = "private/create")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

}
