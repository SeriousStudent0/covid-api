package org.polytech.covidapi.Domain;

import javax.persistence.*;

@Entity
@Table(name="t_patient")
public class Patient {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPatient;

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
}
