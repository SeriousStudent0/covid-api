package org.polytech.covidapi.Services;

import java.util.*;

import javax.persistence.EntityNotFoundException;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public PatientServices(PatientRepository patientRepository, DoctorRepository doctorRepository){
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
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

    public Patient addDoctorToPatient(Integer patientId, Integer doctorId){
        Patient pointedPatient = patientRepository.findById(patientId)
            .orElseThrow(() -> new EntityNotFoundException("Patient Not Found"));

        Doctor pointedDoctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new EntityNotFoundException("Doctor Not Found"));
        
        pointedPatient.setDoctor(pointedDoctor);
        return patientRepository.save(pointedPatient);
    }
}
