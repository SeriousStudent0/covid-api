package org.polytech.covidapi.Services;

import java.util.*;

import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServices(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    @PreAuthorize("hasRole('USER')")
    public List<Patient> getAllPatientByName(String name){
        return patientRepository.findAllByNameContaining(name);
    } 

    @PreAuthorize("hasRole('USER')")
    public List<Patient> getPatientsByDoctorId(Integer doctorId){
        return patientRepository.findAllByDoctorIdDoctor(doctorId);
    } 

    public Patient createPatient(Patient patient){
        patient.setVaccinated(false);
        return patientRepository.save(patient);
    }
}
