package com.swe.sartoria.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String description;
    private String status = "PENDING";
    private Date dueDate;
    private float discount = 0.0f;   // in percentage
    private float totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Costumer costumer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Job> jobs = new ArrayList<>();

    public Order(){
        this.discount = 0.0f;
        this.jobs = new ArrayList<>();
    }
    public void addJob(Job job) {
        jobs.add(job);
        calculateTotalPrice();
    }

    public void removeJob(Job job) {
        jobs.remove(job);
        calculateTotalPrice();
    }

    // Method set final to avoid fragile base class problem
    private final void calculateTotalPrice() {
        float total = 0;
        for (Job job : jobs) {
            total += job.getPrice();
        }
        this.totalPrice = total-(total*discount);
    }
    public void setDiscount(float discount) {
        this.discount = discount;
        calculateTotalPrice();
    }


}
