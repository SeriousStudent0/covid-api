package org.polytech.covidapi.Domain;

import javax.persistence.*;

@Entity
@Table(name="t_patient")
public class Patient {
 
     @Id
    private int id;

    private String name;
    private Address address;
    private boolean vaccinated;
}
