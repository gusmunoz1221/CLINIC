package com.API.Model.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "person")
@RequiredArgsConstructor
@Entity
public class PersonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperson", columnDefinition = "INT(10)")
     Integer idperson;
    @Column(name = "dni", columnDefinition = "VARCHAR(45)")
     String dni;
    @Column(name = "name", columnDefinition = "VARCHAR(45)")
     String name;
    @Column(name = "lastname", columnDefinition = "VARCHAR(45)")
     String lastname;
    @Column(name = "phone", columnDefinition = "VARCHAR(45)")
     String phone;
}
