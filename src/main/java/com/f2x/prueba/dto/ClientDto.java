package com.f2x.prueba.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientDto {

    private int id;
    private String identificationType;
    private String identificationNumber;
    @Min(2)
    private String name;
    @Min(2)
    private String lastname;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String mail;
    private Date dateCreated;
    private Date dateModified;
}
