package com.swe.sartoria.model_domain;

import jakarta.persistence.*;

@Entity
@Table(name = "costumers")
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String email;
    private Long phone;


    public Costumer(String name, String surname, String email, Long phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public Costumer(){
    }


}
