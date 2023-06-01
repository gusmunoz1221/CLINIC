package com.API.Model.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnDtoResponse{
    Integer id;
    String date;
    String hour;
    Boolean treated;
    PatientDto patient;
    DoctorDto doctor;
}
