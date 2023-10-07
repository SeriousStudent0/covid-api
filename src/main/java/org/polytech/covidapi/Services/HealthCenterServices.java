package org.polytech.covidapi.Services;

import java.util.List;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.HealthCenter;
import org.polytech.covidapi.Repository.HealthCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCenterServices {
    private final HealthCenterRepository healthCenterRepository;

    @Autowired
    public HealthCenterServices(HealthCenterRepository healthCenterRepository){
        this.healthCenterRepository = healthCenterRepository;
    }

    public HealthCenter createHealthCenter(HealthCenter healthCenter){
        return healthCenterRepository.save(healthCenter);
    }

    public List<HealthCenter> getAll(){
        return healthCenterRepository.findAll();
    }

    public List<Doctor> getCenterDoctorList(Integer id){
        HealthCenter healthCenter = healthCenterRepository.findByIdCenter(id);
        return healthCenter.getDoctorList();
    }
}
