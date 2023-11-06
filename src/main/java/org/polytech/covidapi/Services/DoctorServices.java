package org.polytech.covidapi.Services;

import java.io.Console;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.print.Doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.polytech.covidapi.Domain.Address;
import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Repository.AddressRepository;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.Repository.HealthCenterRepository;
import org.polytech.covidapi.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DoctorServices implements UserDetailsService{
    private static Logger log = LoggerFactory.getLogger(DoctorServices.class);
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

    @Override
    public UserDetails loadUserByUsername(final String login)
            throws UsernameNotFoundException {
        log.info("gathering of {}", login);

        Optional<Doctor> optionalUser = doctorRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            Doctor user = optionalUser.get();
            return new User(user.getLogin(), user.getPassword(), List.of());
        } else {
            throw new UsernameNotFoundException("The user : " + login + " does not exist");
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    public Doctor createDoctor(Doctor doctor){
        if(doctor.getRole() != UserRole.SUPER_ADMIN){
            HealthCenter healthCenter = healthCenterRepository.findByIdCenter(doctor.getHealthCenter().getId());
            healthCenter.addDoctor(doctor);
            healthCenterRepository.save(healthCenter);
            doctor.setHealthCenter(healthCenter);
        } else{
            doctor.setHealthCenter(null);
        }
        Optional<Address> addressOptional = addressRepository.findById(doctor.getAddress().getId());
        if(addressOptional.isPresent()){
            Address address = addressOptional.get(); // Retrieve the Address object
            doctor.setAddress(address);
        }
        return doctorRepository.save(doctor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteDoctor(Doctor doctor) {
        try {
            doctorRepository.deleteById(doctor.getId());
            return true; // Deletion successful
        } catch (Exception e) {
            return false; // Handle any exceptions, e.g., doctor not found, and indicate failure
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Doctor getDoctor(Integer doctorId){
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new EntityNotFoundException("Doctor Not Found"));
        
        return doctor;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Doctor updateDoctor(Doctor updatedDoctor){
        Doctor existingDoctor = doctorRepository.findById(updatedDoctor.getId())
            .orElseThrow(() -> new EntityNotFoundException("Doctor Not Found"));
        
        Address existingAddress = addressRepository.findById(updatedDoctor.getAddress().getId())
            .orElseThrow(() -> new EntityNotFoundException("Address Not Found"));

        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setLogin(updatedDoctor.getLogin());
        existingDoctor.setPassword(updatedDoctor.getPassword());
        if(updatedDoctor.getRole() != UserRole.SUPER_ADMIN){
            existingDoctor.setHealthCenter(updatedDoctor.getHealthCenter());
        }
        existingDoctor.setAddress(existingAddress);

        return doctorRepository.save(existingDoctor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Doctor> getDoctorsByHealthCenterId(Integer healtCenterId){
        return doctorRepository.findAllByHealthcenterIdCenter(healtCenterId);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Doctor> getSuperAdminList(){
        return doctorRepository.findAllByRole(UserRole.SUPER_ADMIN);
    }

    public Integer authenticate(String login, String password){
        Optional<Doctor> optionalDoctor = doctorRepository.findByLogin(login);
        
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            
            if (passwordMatches(password, doctor.getPassword())) {
                doctor.setIsLogged(true);
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

    @PreAuthorize("hasRole('USER')")
    public boolean loggout(Integer id){
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setIsLogged(false);
            doctor = doctorRepository.save(doctor);
            return true;
        }
        return false;
    }
}
