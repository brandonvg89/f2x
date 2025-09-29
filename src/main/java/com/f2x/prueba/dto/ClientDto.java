package com.f2x.prueba.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private int id;
    private String identificationType;
    private String identificationNumber;
    @Size(min = 2, message = "The name must have at least 2 characters")
    private String name;
    @Size(min = 2, message = "The lastname must have at least 2 characters")
    private String lastname;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String mail;
    private Date dateCreated;
    private Date dateModified;
}
