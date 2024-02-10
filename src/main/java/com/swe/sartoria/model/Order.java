package com.swe.sartoria.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String description;
    private String status;
    @Getter
    private Date dueDate;

    // TODO: implement option for discount
    // TODO: implement and test method for calculating total price


    @ManyToOne(fetch = FetchType.EAGER)
    private Costumer costumer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Job> jobs;



    public void addJob(Job job) {
        jobs.add(job);
    }

    public void removeJob(Job job) {
        jobs.remove(job);
    }


}
