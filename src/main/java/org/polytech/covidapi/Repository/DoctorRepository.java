package org.polytech.covidapi.Repository;

import java.util.List;
import java.util.Optional;

import org.polytech.covidapi.Domain.*;
import org.polytech.covidapi.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    
    Optional<Doctor> findById(Integer doctorId);
    
    List<Doctor> findAllByHealthcenterIdCenter(Integer healthCenterId);

    Optional<Doctor> findByLogin(String login);

    Optional<Doctor> findByRole(UserRole role);

    
}
