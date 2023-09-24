package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_health_center")
public class HeathCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCenter;

    private String name;

    @OneToOne
    @JoinColumn(
        name = "idAddress",
        foreignKey = @ForeignKey(name = "addressFK"),
        nullable = true)
    private Address centerAddress;

    @OneToMany(mappedBy = "healthcenters")
    private List<Doctor> doctors;

}
