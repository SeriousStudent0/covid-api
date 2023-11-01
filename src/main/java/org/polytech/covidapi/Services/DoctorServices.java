package org.polytech.covidapi.Services;

import java.io.Console;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.print.Doc;

import org.polytech.covidapi.Domain.Address;
import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Repository.AddressRepository;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.Repository.HealthCenterRepository;
import org.polytech.covidapi.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServices {

    private final DoctorRepository doctorRepository;
    private final HealthCenterRepository healthCenterRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public DoctorServices(DoctorRepository doctorRepository, HealthCenterRepository healthCenterRepository, AddressRepository addressRepository){
        this.doctorRepository = doctorRepository;
        this.healthCenterRepository = healthCenterRepository;
        this.addressRepository = addressRepository;
    }

    public void createSuperAdminIfNotExists() {
        // Check if a SUPER_ADMIN user already exists
        Optional<Doctor> existingSuperAdmin = doctorRepository.findByRole(UserRole.SUPER_ADMIN);

        if (!existingSuperAdmin.isPresent()) {
            // Create a new SUPER_ADMIN user
            Doctor superAdmin = new Doctor();
            superAdmin.setName("administrator");
            superAdmin.setLogin("admin");
            superAdmin.setPassword("password"); // should be securely hashed
            superAdmin.setRole(UserRole.SUPER_ADMIN);
            doctorRepository.save(superAdmin);
        }
    }

    public Doctor createDoctor(Doctor doctor){
        HealthCenter healthCenter = healthCenterRepository.findByIdCenter(doctor.getHealthCenter().getId());
        healthCenter.addDoctor(doctor);
        healthCenterRepository.save(healthCenter);
        doctor.setHealthCenter(healthCenter);
        Optional<Address> addressOptional = addressRepository.findById(doctor.getAddress().getId());
        if(addressOptional.isPresent()){
            Address address = addressOptional.get(); // Retrieve the Address object
            doctor.setAddress(address);
        }
        return doctorRepository.save(doctor);
    }

    public boolean deleteDoctor(Doctor doctor) {
        try {
            doctorRepository.delete(doctor);
            return true; // Deletion successful
        } catch (Exception e) {
            return false; // Handle any exceptions, e.g., doctor not found, and indicate failure
        }
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

    public boolean loggout(Integer id){
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setLog(false);
            doctor = doctorRepository.save(doctor);
            return true;
        }
        return false;
    }
}
