package org.polytech.covidapi.Domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="t_patient")
public class Patient {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    @JsonProperty("id")
    private Integer idPatient;

    private String name;
    private boolean vaccinated;

    @ManyToOne
    @JoinColumn(name = "idDoctor")
    private Doctor doctor;

    public Integer getId(){
        return idPatient;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean getVaccinated(){
        return vaccinated;
    }

    public void setVaccinated(boolean status){
        this.vaccinated = status;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }
}

