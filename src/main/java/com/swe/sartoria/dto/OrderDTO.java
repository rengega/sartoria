package com.swe.sartoria.dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private CostumerDTO costumer;
    private String description;
    private List<JobDTO> jobs;
    private float price;
    private float discount;
    private String status;
    private Date dueDate;
}
