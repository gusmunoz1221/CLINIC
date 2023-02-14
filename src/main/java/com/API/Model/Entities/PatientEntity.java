package com.API.Model.Entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Table(name = "patient")
@RequiredArgsConstructor
@Entity
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpatient", columnDefinition = "INT(10)")
     Integer id;
    @Column(name = "address", columnDefinition = "VARCHAR(45)")
     String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "idperson")
    PersonEntity personEntity;
}
