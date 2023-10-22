package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="t_health_center")
public class HealthCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_healthcenter")
    @JsonProperty("id")
    private Integer idCenter;

    private String name;

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address address;

    @OneToMany(mappedBy = "healthcenter")
    @JsonProperty("doctorList")
    private List<Doctor> doctors;

    public Integer getId() {
        return idCenter;
    }

    public void setId(Integer id){
        this.idCenter = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Address getAddress(){
        return address;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public List<Doctor> getDoctorList(){
        return doctors;
    }

    public void addDoctor(Doctor doctor){
        if(doctor != null){
            doctors.add(doctor);
            doctor.setHealthCenter(this);
        }
    }

    public void removeDoctorById(Integer doctorId){
        doctors.removeIf(doctor -> doctor.getId().equals(doctorId));
    }
}
