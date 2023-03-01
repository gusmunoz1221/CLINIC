package com.API.Model.Entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;

@Data
@Table(name = "doctor")
@RequiredArgsConstructor
@Entity
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddoctor", columnDefinition = "INT(10)")
    Integer id;
    @Column(name = "profession", columnDefinition = "VARCHAR(45)")
    String profession;
    @Column(name = "consultation", columnDefinition = "VARCHAR(45)")
    int consultation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "idperson")
    PersonEntity personEntity;
}
