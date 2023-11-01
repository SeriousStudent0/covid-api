package org.polytech.covidapi.Services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Repository.AddressRepository;
import org.polytech.covidapi.Repository.HealthCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCenterServices {
    private final HealthCenterRepository healthCenterRepository;

    @Autowired
    public HealthCenterServices(
        HealthCenterRepository healthCenterRepository,
        AddressRepository addressRepository){
        this.healthCenterRepository = healthCenterRepository;
    }

    public HealthCenter createHealthCenter(HealthCenter healthCenter){
        
        return healthCenterRepository.save(healthCenter);
    }

    public HealthCenter updateHealthCenter(HealthCenter healthCenter){
        HealthCenter existingCenter = healthCenterRepository.findById(healthCenter.getId())
            .orElseThrow(() -> new EntityNotFoundException("HealthCenter Not Found"));

        existingCenter.setName(healthCenter.getName());

        existingCenter.setAddress(healthCenter.getAddress());
        return healthCenterRepository.save(existingCenter);
    }

    public List<HealthCenter> getAll(){
        return healthCenterRepository.findAll();
    }

    public List<Doctor> getCenterDoctorList(Integer id){
        HealthCenter healthCenter = healthCenterRepository.findByIdCenter(id);
        return healthCenter.getDoctorList();
    }
}
