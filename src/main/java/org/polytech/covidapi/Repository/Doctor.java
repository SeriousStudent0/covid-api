package org.polytech.covidapi.Repository;

import javax.persistence.Id;

public class Doctor {
    
    @Id
    private int id;

    private String name;
}
