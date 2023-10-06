package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_doctor")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Integer idDoctor;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "idCenter")
    private HealthCenter healthcenter;

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address doctorAddress;

    @OneToMany(mappedBy = "doctor")
    private List<RendezVous> rdv;


    public Integer getId(){
        return idDoctor;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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
