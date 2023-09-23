package org.polytech.covidapi.Domain;

import javax.persistence.*;

@Entity
@Table(name="t_address")
public class Address {
    
    @Id
    private int id;

    private String country;
    private String city;
    private String street;
    private double streetNumber;
}
