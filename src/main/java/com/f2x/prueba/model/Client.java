package com.f2x.prueba.model;

import com.f2x.prueba.dto.ClientDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Table
@Entity(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "identification_type")
    private String identificationType;

    @Column(name = "identification_number")
    private String identificationNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "mail")
    private String mail;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_modified")
    private Date dateModified;

    public ClientDto convertToDto() {
        return ClientDto.builder()
                .id(id)
                .identificationType(identificationType)
                .identificationNumber(identificationNumber)
                .name(name)
                .lastname(lastname)
                .mail(mail)
                .dateCreated(dateCreated)
                .dateModified(dateModified)
                .build();
    }
}
