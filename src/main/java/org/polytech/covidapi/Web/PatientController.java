package org.polytech.covidapi.Web;

import java.util.List;

import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PatientController {

    private final PatientServices patientService;

    @Autowired
    private PatientController (PatientServices patientService){
        this.patientService = patientService;
    }

    @GetMapping(path = "/patient/{doctorId}/patients")
    public List<Patient> getAllPatientForDoctorList(@PathVariable Integer doctorId){
        return patientService.getPatientsByDoctorId(doctorId);
    }

}
