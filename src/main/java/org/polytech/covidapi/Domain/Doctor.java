package org.polytech.covidapi.Domain;

import javax.persistence.*;

@Entity
@Table(name="t_doctor")
public class Doctor {
    
    @Id
    private int id;

    private String name;
}
