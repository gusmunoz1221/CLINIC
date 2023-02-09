package com.API.Model.mappers;

import com.API.Model.Dtos.DoctorDto;
import com.API.Model.Dtos.EntityMessageDto;
import com.API.Model.Entities.DoctorEntity;
import com.API.Model.Entities.PersonEntity;
import com.API.Model.repositories.PersonRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class DoctorMapper {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public DoctorMapper(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public DoctorDto doctortEntityToDto(DoctorEntity doctorEntity) {
        return Optional.ofNullable(doctorEntity)
                .map(entity -> new DoctorDto(
                                            entity.getProfession(),
                                            entity.getConsultation(),
                                            personRepository.findById(entity.getId())
                                            .map(personMapper::personentityToDto)
                                            .orElse(null)
                                            )
                      ).orElse(new DoctorDto());
    }

   /*-en el caso que la persona no exista en la base de datos se guardan los datos del medico y de la persona
     -en el caso que la persona exista se guardan solamente los datos del medico*/
    public DoctorEntity doctorDtoToEntity(DoctorDto doctorDto, PersonEntity personEntity){
        DoctorEntity entity = new DoctorEntity();

        entity.setProfession(doctorDto.getProfession());
        entity.setConsultation(doctorDto.getConsultation());
        entity.setPersonEntity(personEntity);
        return entity;
    }
    public DoctorEntity doctorDtoToEntitymodify(DoctorDto doctorDto, PersonEntity personEntity) {
        DoctorEntity entity = new DoctorEntity();

        entity.setProfession(doctorDto.getProfession());
        entity.setConsultation(doctorDto.getConsultation());
        return entity;
    }

    public EntityMessageDto entityMessageDto(DoctorEntity doctorEntity){
        EntityMessageDto entityMessageDto = new EntityMessageDto();

        entityMessageDto.setId(doctorEntity.getId());

        return entityMessageDto;
    }
}

/*
        Como es solo un atributo, no utilizamos el Optional

    public ContactMessageDto ContactEntityToMessage(DoctorEntity contactEntity){
        ContactMessageDto contactMessage =  new ContactMessageDto();

        contactMessage.setId(contactEntity.getId());
        return contactMessage;
    }
    */
