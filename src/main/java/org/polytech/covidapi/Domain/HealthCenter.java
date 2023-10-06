package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_health_center")
public class HealthCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCenter;

    private String name;

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address centerAddress;

    @OneToMany(mappedBy = "healthcenters")
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
}