package com.API.Model.Entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Table(name = "turn")
@RequiredArgsConstructor
@Entity
public class TurnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idturn", columnDefinition = "INT(10)")
    Integer id;
    @Column(name = "date", columnDefinition = "DATE")
    LocalDate date;
    @Column(name = "hour", columnDefinition = "TIME")
    LocalTime hour;
    @Column(name = "treated", columnDefinition = "TINYINT")
    Boolean treated;
    @Column(name = "idpatient", columnDefinition = "INT(10)")
    Integer idPatient;
    @Column(name = "iddoctor", columnDefinition = "INT(10)")
    Integer idDoctor;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "idpatient", insertable = false, updatable = false)
    PatientEntity patientEntity;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "iddoctor", insertable = false, updatable = false)
    DoctorEntity doctorEntity;
}
