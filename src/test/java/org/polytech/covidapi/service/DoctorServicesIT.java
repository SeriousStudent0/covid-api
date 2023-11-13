package org.polytech.covidapi.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.Services.DoctorServices;
import org.polytech.covidapi.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class DoctorServicesIT {
    
    /*
    @Autowired
    DoctorServices doctorServices;

    @Autowired
    DoctorRepository doctorRepository;

    @Test
    public void iTloginTest(){

        var user = new Doctor();
        user.setName("userTestName");
        user.setLogin("userTest");
        user.setRole(UserRole.SUPER_ADMIN);
        user.setIsLogged(false);
        user.setId(2);

        // Encode the password using BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("PasswordTest");
        user.setPassword(encodedPassword);

        doctorRepository.save(user);

        var userId = doctorServices.authenticate("userTest", "PasswordTest");

        Optional<Doctor> optionalUser = doctorRepository.findById(2);
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }

        Assertions.assertThat(userId).isNotNull();
        Assertions.assertThat(userId).isEqualTo(2);
        Assertions.assertThat(user.getIsLogged()).isTrue();
    }

    @Test
    public void iTloginUnknownUserTest(){

        var user = new Doctor();
        user.setName("userTestName");
        user.setLogin("userTest");
        user.setRole(UserRole.SUPER_ADMIN);
        user.setIsLogged(false);
        user.setId(2);

        // Encode the password using BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("PasswordTest");
        user.setPassword(encodedPassword);

        doctorRepository.save(user);
  
        var userId = doctorServices.authenticate("WrongLogin", "WrongPassword");

        Optional<Doctor> optionalUser = doctorRepository.findById(2);
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }
        
        Assertions.assertThat(userId).isNull();
        Assertions.assertThat(user.getIsLogged()).isFalse();

    }

     */
}
