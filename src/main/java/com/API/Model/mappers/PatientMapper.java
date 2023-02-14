package com.API.Model.mappers;

import com.API.Model.Dtos.EntityMessageDto;
import com.API.Model.Dtos.PatientDto;
import com.API.Model.Entities.PatientEntity;
import com.API.Model.Entities.PersonEntity;
import com.API.Model.repositories.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientMapper {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PatientMapper(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public PatientDto patientEntityToDto(PatientEntity patientEntity) {
        return Optional.ofNullable(patientEntity)
                .map(entity -> new PatientDto(
                                             entity.getAddress(),
                                             personRepository.findById(entity.getPersonEntity().getId())
                                            .map(personMapper::personentityToDto)
                                            .orElse(null)
                                            )
                    )
                .orElse(new PatientDto());
    }

    public PatientEntity patientDtoToEntity(PatientDto patientDto, PersonEntity personEntity){
        PatientEntity patient = new PatientEntity();

        patient.setAddress(patientDto.getAddress());
        patient.setPersonEntity(personEntity);

        return patient;
    }

    public EntityMessageDto entityMessageDto(PatientEntity patientEntity){
        EntityMessageDto entityMessageDto = new EntityMessageDto();

        entityMessageDto.setMessage("paciente con ID: "+patientEntity.getId()+" se agrego correctamente");
        return entityMessageDto;
    }
}
