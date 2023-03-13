package com.API.Model.mappers;

import com.API.Model.Dtos.TurnDto;
import com.API.Model.Dtos.TurnDtoResponse;
import com.API.Model.Entities.TurnEntity;
import com.API.Model.repositories.DoctorRepository;
import com.API.Model.repositories.PatientRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class TurnMapper{
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;


    public TurnMapper(PatientRepository patientRepository, DoctorMapper doctorMapper, DoctorRepository doctorRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.doctorMapper = doctorMapper;
        this.doctorRepository = doctorRepository;
        this.patientMapper = patientMapper;
    }

    public TurnDtoResponse turnEntityToDto(TurnEntity turnEntity){
        return Optional.ofNullable(turnEntity)
                .map(entity -> new TurnDtoResponse(
                                                entity.getId(),
                                                String.valueOf(entity.getHour()),
                                                String.valueOf(entity.getDate()),
                                                entity.getTreated(),
                                                patientRepository.findById(entity.getId())
                                                                 .map(patientMapper::patientEntityToDto)
                                                                 .orElse(null),
                                                doctorRepository.findById(entity.getId())
                                                                .map(doctorMapper::doctortEntityToDto)
                                                                .orElse(null)
                                                    )
                    )
                .orElse(new TurnDtoResponse());
    }
    public TurnEntity turnDtoToEntity(TurnDto turnDto){
        TurnEntity entity = new TurnEntity();

        entity.setDate(LocalDate.parse(turnDto.getDate()));
        entity.setHour(LocalTime.parse(turnDto.getHour()));
        entity.setTreated(turnDto.getTreated());
        entity.setIdDoctor(turnDto.getIddoctor());
        entity.setIdPatient(turnDto.getIdpatient());

        return entity;
    }

    public TurnEntity turnDtoToEntityModify(TurnDto turnDto,int id){
        TurnEntity entity = new TurnEntity();

        entity.setId(id);
        entity.setDate(LocalDate.parse(turnDto.getDate()));
        entity.setHour(LocalTime.parse(turnDto.getHour()));
        entity.setTreated(turnDto.getTreated());
        entity.setIdDoctor(turnDto.getIddoctor());
        entity.setIdPatient(turnDto.getIdpatient());

        return entity;
    }

}
