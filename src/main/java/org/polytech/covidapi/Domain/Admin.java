package org.polytech.covidapi.Domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Integer idAdmin;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private HealthCenter healthcenters;

}
