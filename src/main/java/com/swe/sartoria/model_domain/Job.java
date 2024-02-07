package com.swe.sartoria.model_domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private float price;

    public Job(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Job(){
    }

}
