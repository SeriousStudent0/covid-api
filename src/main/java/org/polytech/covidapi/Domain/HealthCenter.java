package org.polytech.covidapi.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //@OneToMany(mappedBy = "healthcenter", fetch = FetchType.LAZY)
    //@JsonProperty("doctorList")
    //@JsonIgnore
    private List<Doctor> doctorList = new ArrayList<>();

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
        return doctorList;
    }

    public void addDoctor(Doctor doctor){
        System.out.println("Hello, World!");
        //if(doctor != null){
        //    doctorList.add(doctor);
        //}
    }

    public void removeDoctorById(Integer doctorId){
        doctorList.removeIf(doctor -> doctor.getId().equals(doctorId));
    }
}
