package org.polytech.covidapi.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.polytech.covidapi.enums.UserRole;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="t_doctor")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Integer idDoctor;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    //@JsonProperty("isLogged")
    private Boolean isLogged = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    //@JsonProperty("healthcenter")
    @ManyToOne
    @JoinColumn(name = "idCenter")
    @JsonIgnore
    private HealthCenter healthcenter;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Patient> patients = new ArrayList<>();

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address doctorAddress;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<RendezVous> rdv = new ArrayList<>();


    public Integer getId(){
        return idDoctor;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Boolean getIsLogged(){
        return isLogged;
    }

    public void setIsLogged(Boolean logStatus){
        this.isLogged = logStatus;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public UserRole getRole(){
        return role;
    }

    public void setRole(UserRole role){
        this.role = role;
    }

    public Address getAddress(){
        return doctorAddress;
    }

    public void setAddress(Address address){
        this.doctorAddress = address;
    }

    public HealthCenter getHealthCenter(){
        return healthcenter;
    }

    public void setHealthCenter(HealthCenter healthCenter){
        this.healthcenter = healthCenter;
    }

    public List<Patient> getPatients(){
        return patients;
    }

    public void addPatient(Patient patient){
        if (patient != null){
            patients.add(patient);
            patient.setDoctor(this);
        }
    }

    public void removePatientById(Integer patientId){
        patients.removeIf(patient -> patient.getId().equals(patientId));
    }

    public List<RendezVous> getRDV(){
        return rdv;
    }

    public void addRDV(RendezVous newRDV){
        if(newRDV != null){
            rdv.add(newRDV);
            newRDV.setDoctor(this);
        }
    }

    public void removeRDVById(Integer rdvId){
        rdv.removeIf(rendezVous -> rendezVous.getId().equals(rdvId));
    }


}
