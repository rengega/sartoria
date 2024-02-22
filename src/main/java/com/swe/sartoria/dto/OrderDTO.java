package com.swe.sartoria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private CostumerDTO costumer;
    private String description;
    private List<JobDTO> jobs;
    private float totalPrice;
    @Builder.Default
    private float discount = 0;
    @Builder.Default
    private String status = "PENDING";
    private Date dueDate;
    private Boolean paid = false;
}
