package org.polytech.covidapi.Services;

import java.util.*;

import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServices(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatientByName(String name){
        return patientRepository.findByNameContaining(name);
    } 

    public List<Patient> getPatientsByDoctorId(Integer doctorId){
        return patientRepository.findByDoctorIdDoctor(doctorId);
    } 
}
