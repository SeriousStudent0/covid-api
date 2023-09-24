package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_doctor")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDoctor;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private HeathCenter healthcenters;

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
}
