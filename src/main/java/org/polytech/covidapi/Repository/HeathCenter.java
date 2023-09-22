package org.polytech.covidapi.Repository;

import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HeathCenter {
    
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;

}
