package com.swe.sartoria.dto;

import com.swe.sartoria.model.Costumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CostumerDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Long phone;


}
