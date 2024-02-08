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

    public long getId() {
        return Long.valueOf(id);
    }

    // defensive copy
    public String getEmail(){
        return String.valueOf(email);
    }

    public String getName() {
        return String.valueOf(name);
    }

    public String getSurname() {
        return String.valueOf(surname);
    }

    public Long getPhone() {
        return Long.valueOf(phone);
    }


    public void setName(String nome1) {
        this.name = nome1;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Costumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
