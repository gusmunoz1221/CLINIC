package com.API.services;

import com.API.Exceptions.BadRequestException;
import com.API.Exceptions.NotFoundException;
import com.API.Model.Dtos.EntityMessageDto;
import com.API.Model.Dtos.PatientDto;
import com.API.Model.Dtos.PersonDto;
import com.API.Model.Entities.PatientEntity;
import com.API.Model.Entities.PersonEntity;
import com.API.Model.mappers.PatientMapper;
import com.API.Model.mappers.PersonMapper;
import com.API.Model.repositories.PatientRepository;
import com.API.Model.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper, PersonRepository personRepository, PersonMapper personMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    /*busca un paciente por el id ingresado*/
    public PatientDto findPatientById(int id) {
        return patientRepository.findById(id)
                .map(patientMapper::patientEntityToDto)
                .orElse(null);
    }

    /* busca todos los pacientes registrados en la bd */
    public List<PatientDto> findAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::patientEntityToDto)
                .toList();
    }

    /*-Agregamos un paciente a la bd*/
    public EntityMessageDto addPatient(PatientDto patientDto){
        if (patientDto.getPerson() == null)
            throw new BadRequestException("No se cargaron los datos de la persona...");
        if (patientDto.getAddress().equals(null))
            throw new BadRequestException("direccion vacia...");

        /*dni es unico ---> personas residentes de argentina*/
        if (!personRepository.existsByDni(patientDto.getPerson().getDni())){
            PersonEntity personEntity = personMapper.personDtoToEntity(patientDto.getPerson());

            return Optional
                    .ofNullable(patientDto)
                    .map(dto -> patientMapper.patientDtoToEntity(patientDto, personEntity))
                    .map(entity -> patientRepository.save(entity))
                    .map(entity -> patientMapper.entityMessageDto(entity))
                    .orElse(new EntityMessageDto());
         }
        /* -si existe la persona, buscamos por dni y guardamos la guaramos en personEntity
         * de manera tal que no se repitan las mismas personas en diferentes tablas
         * -PersonEntity hace referencia al id de persona en la tabla "patient" */
        PersonEntity personEntity = personRepository.findByDni(patientDto.getPerson().getDni());
        return Optional
                .ofNullable(patientDto)
                .map(dto -> patientMapper.patientDtoToEntity(patientDto, personEntity))
                .map(entity -> patientRepository.save(entity))
                .map(entity -> patientMapper.entityMessageDto(entity))
                .orElse(new EntityMessageDto());
    }

    public boolean deletePatientById(int id){
        if(!patientRepository.existsById(id))
            throw new NotFoundException("no se encontro el paciente que desea eliminar");
        patientRepository.deleteById(id);
        return true;
    }

    public PatientDto modifyPatient(PatientDto patientDto, int id){
        if (patientDto.getPerson() == null)
            throw new BadRequestException("No se cargaron los datos de la persona...");
        if (patientDto.getAddress().equals(null))
            throw new BadRequestException("direccion vacia...");
        if (patientDto.getAddress().equals(null))
            throw new BadRequestException("direccion vacia...");

        int idPerson = patientRepository.findById(id).get().getPersonEntity().getId();
        PersonEntity personEntity = personMapper.personDtoToEntity(patientDto.getPerson());
        personEntity.setId(idPerson);
        personRepository.save(personEntity);

        return Optional
                .ofNullable(patientDto)
                .map(dto -> patientMapper.patientDtoToEntityModify(patientDto, personEntity, id))
                .map(entity -> patientRepository.save(entity))
                .map(entity -> patientMapper.patientEntityToDto(entity))
                .orElse(new PatientDto());
    }
}
