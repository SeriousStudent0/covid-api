package org.polytech.covidapi.Services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServices {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServices(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctor(Integer doctorId){
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new EntityNotFoundException("Doctor Not Found"));
        
        return doctor;
    }

    public Doctor updateDoctor(Integer doctorId, Doctor updatedDoctor){
        Doctor existingDoctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new EntityNotFoundException("Doctor Not Found"));

        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setHealthCenter(updatedDoctor.getHealthCenter());
        existingDoctor.setAddress(updatedDoctor.getAddress());

        return doctorRepository.save(existingDoctor);
    }

    public List<Doctor> getDoctorsByHealthCenterId(Integer healtCenterId){
        return doctorRepository.findAllByHealthcenterIdCenter(healtCenterId);
    }

    public Integer authenticate(String login, String password){
        Optional<Doctor> optionalDoctor = doctorRepository.findByLogin(login);
        
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            
            if (passwordMatches(password, doctor.getPassword()) && doctor.getRole() != UserRole.USER) {
                doctor.setLog(true);
                doctor = doctorRepository.save(doctor);
                return doctor.getId();
            }
        }

        return null;
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        // Password hashing logic here
        // hashing library (BCrypt)
        return rawPassword.equals(hashedPassword);
    }
    
}
