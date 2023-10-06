package org.polytech.covidapi.Domain;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="t_rdv")
public class RendezVous {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRDV;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "idDoctor")
    private Doctor doctor;

    @OneToOne
    private Patient patient;


    public Integer getId(){
        return idRDV;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public Patient getPatient(){
        return patient;
    }

    public void setPatient(Patient patient){
        this.patient = patient;
    }

    public void test(){
        
    }
}
 
     
