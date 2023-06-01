package com.API.Model.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnDto {
    String date;
    String hour;
    Boolean treated;
    Integer idpatient;
    Integer iddoctor;
}
