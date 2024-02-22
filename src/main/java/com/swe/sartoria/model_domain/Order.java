package com.swe.sartoria.model_domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String description;
    private String status;
    private Date dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Costumer costumer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Job> jobs;

    // setters and getters

    public Order() {
    }
    public Order(String description, String status, Costumer costumer, Date dueDate) {
        this.description = description;
        this.status = status;
        this.costumer = costumer;
        this.jobs = new ArrayList<>();
        this.dueDate = dueDate;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    public List<Job> getJobs() {
        return List.copyOf(jobs);
    }

    public void addJob(Job job) {
        jobs.add(job);
    }

    public void removeJob(Job job) {
        jobs.remove(job);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dueDate=" + dueDate +
                ", costumer=" + costumer +
                ", jobs=" + jobs +
                '}';
    }

}