package model_domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private String status;

    @ManyToOne
    private Costumer costumer;

    @OneToMany
    private List<Job> jobs;

}
