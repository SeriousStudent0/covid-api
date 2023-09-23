package org.polytech.covidapi.Domain;

import javax.persistence.*;

@Entity
@Table(name="t_health_center")
public class HeathCenter {
    
    @Id
    private int id;

    private String name;
    private String address;

}
