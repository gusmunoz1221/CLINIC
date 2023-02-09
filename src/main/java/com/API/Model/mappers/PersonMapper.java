package com.API.Model.mappers;

import com.API.Model.Dtos.PersonDto;
import com.API.Model.Entities.PersonEntity;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PersonMapper {
    public PersonDto personentityToDto(PersonEntity entity){
        return Optional
                .ofNullable(entity)
                .map(
                        ent -> new PersonDto(
                                                ent.getDni(),
                                                ent.getName(),
                                                ent.getLastname(),
                                                ent.getPhone()
                                                )
                    ).orElse(new PersonDto());
    }

    public  PersonEntity personDtoToEntity(PersonDto dto){
        PersonEntity entity = new PersonEntity();

        entity.setDni(dto.getDni());
        entity.setName(dto.getName());
        entity.setLastname(dto.getLastname());
        entity.setPhone(dto.getPhone());

        return entity;
    }
}
