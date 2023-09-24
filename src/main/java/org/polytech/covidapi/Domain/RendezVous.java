package org.polytech.covidapi.Domain;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="t_rdv")
public class RendezVous {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRDV;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private Doctor doctor;

    @OneToOne
    private Patient patient;
}
 
     
