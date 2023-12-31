package org.polytech.covidapi.Repository;

import java.util.List;

import org.polytech.covidapi.Domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    
    List<Patient> findAllByDoctorIdDoctor(Integer doctorId);
    List<Patient> findAllByNameContaining(String name);
}
