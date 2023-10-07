package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_health_center")
public class HealthCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_healthcenter")
    private Integer idCenter;

    private String name;

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address centerAddress;

    @OneToMany(mappedBy = "healthcenter")
    private List<Doctor> doctors;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Address getAddress(){
        return centerAddress;
    }

    public void setAddress(Address address){
        this.centerAddress = address;
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
