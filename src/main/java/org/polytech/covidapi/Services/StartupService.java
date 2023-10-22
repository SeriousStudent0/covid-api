package org.polytech.covidapi.Services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartupService {
    
    @Autowired
    private DoctorServices doctorServices;

    @PostConstruct
    public void init(){
        doctorServices.createSuperAdminIfNotExists();
    }
}
