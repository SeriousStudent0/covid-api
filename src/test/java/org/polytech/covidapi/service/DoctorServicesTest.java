package org.polytech.covidapi.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Repository.AddressRepository;
import org.polytech.covidapi.Repository.DoctorRepository;
import org.polytech.covidapi.Repository.HealthCenterRepository;
import org.polytech.covidapi.Repository.RendezVousRepository;
import org.polytech.covidapi.Services.DoctorServices;
import org.polytech.covidapi.enums.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DoctorServicesTest {
    
    DoctorServices doctorServices;
    DoctorRepository doctorRepository;
    HealthCenterRepository healthCenterRepository;
    AddressRepository addressRepository;
    RendezVousRepository rendezVousRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @BeforeEach
    void setUp(){
        //creation of "fake" objects so the real doctorService can use them as entry
        doctorRepository = Mockito.mock(DoctorRepository.class);
        healthCenterRepository = Mockito.mock(HealthCenterRepository.class);
        addressRepository = Mockito.mock(AddressRepository.class);
        rendezVousRepository = Mockito.mock(RendezVousRepository.class);
        doctorServices = new DoctorServices(doctorRepository, healthCenterRepository, addressRepository, rendezVousRepository,passwordEncoder);
    }

    @Test
    public void loginTest(){

        var user = new Doctor();
        user.setName("userTestName");
        user.setLogin("userTest");
        user.setPassword(passwordEncoder.encode("PasswordTest"));
        user.setRole(UserRole.SUPER_ADMIN);
        user.setIsLogged(false);

        var userWithId = new Doctor();
        userWithId.setId(2);

        // We here set the expected responses of the fake objects as the real ones would
        // In this case we set the response of the fake objects for the fields we are intrested in (which ones the authenticate() method uses) 
        Mockito.doReturn(Optional.of(user)).when(doctorRepository).findByLogin("userTest");
        Mockito.doReturn(userWithId).when(doctorRepository).save(user);

        var userId = doctorServices.authenticate("userTest", "PasswordTest");

        Assertions.assertThat(userId).isNotNull();
        Assertions.assertThat(userId).isEqualTo(2);
        Assertions.assertThat(user.getIsLogged()).isTrue();

    }

    @Test
    public void loginUnknownUserTest(){

        var user = new Doctor();
        user.setName("userTestName");
        user.setLogin("userTest");
        user.setPassword(passwordEncoder.encode("PasswordTest"));
        user.setRole(UserRole.SUPER_ADMIN);
        user.setIsLogged(false);

        var userWithId = new Doctor();
        userWithId.setId(2);
        //Is the database erased between each test ? if yes -> better create a user eventhough not logging to it 

        // We here set the expected responses of the fake objects as the real ones would
        // In this case we set the response of the fake objects for the fields we are intrested in (which ones the authenticate() method uses) 
        Mockito.doReturn(Optional.of(user)).when(doctorRepository).findByLogin("userTest");
        Mockito.doReturn(userWithId).when(doctorRepository).save(user);

        var userId = doctorServices.authenticate("WrongLogin", "WrongPassword");

        Assertions.assertThat(userId).isNull();
        Assertions.assertThat(user.getIsLogged()).isFalse();

    }
}
