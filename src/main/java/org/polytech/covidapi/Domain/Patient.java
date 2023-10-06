package org.polytech.covidapi.Domain;

import javax.persistence.*;
import javax.print.Doc;

@Entity
@Table(name="t_patient")
public class Patient {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPatient;

    private String name;
    private boolean vaccinated;

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address patientAddress;

    @ManyToOne
    private Doctor doctor;

    @OneToOne(mappedBy = "patient")
    private RendezVous rdv;



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

    public void setVaccinated(){
        this.vaccinated = true;
    }

    public Address getAddress(){
        return patientAddress;
    }

    public void setAddress(Address address){
        this.patientAddress = address;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public RendezVous getRDV(){
        return rdv;
    }

    public void setRDV(RendezVous rdv){
        this.rdv = rdv;
    }
}

