package com.swe.sartoria.dto;

import com.swe.sartoria.model.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private Long id;
    private String name;
    private String description;
    private float price;
    private String category;

}
