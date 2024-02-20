package com.swe.sartoria.dto;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private Long phone;
}
