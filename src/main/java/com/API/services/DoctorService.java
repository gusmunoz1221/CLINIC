package com.API.services;

import com.API.Exceptions.BadRequestException;
import com.API.Model.Dtos.DoctorDto;
import com.API.Model.Dtos.EntityMessageDto;
import com.API.Model.Entities.PersonEntity;
import com.API.Model.mappers.DoctorMapper;
import com.API.Model.mappers.PersonMapper;
import com.API.Model.repositories.DoctorRepository;
import com.API.Model.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper, PersonRepository personRepository, PersonMapper personMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public DoctorDto findDoctorById(int id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::doctortEntityToDto)
                .orElse(null);
    }

    public List<DoctorDto> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::doctortEntityToDto)
                .toList();
    }

    /*-Agregamos un doctor a la base de datos*/
    public EntityMessageDto addDoctor(DoctorDto doctorDto){
        if (doctorDto.getPerson() == null)
            throw new BadRequestException("No se cargaron los datos de la persona...");
        if (doctorDto.getConsultation() < 0)
            throw new BadRequestException("La consulta no peude ser menor a 0");
        if (doctorDto.getProfession().equals(null))
            throw new BadRequestException("la profesion ingresada es incorrecta");

        /*dni es unico ---> personas de solo argentina*/
        if (!personRepository.existsByDni(doctorDto.getPerson().getDni())){
            PersonEntity personEntity = personMapper.personDtoToEntity(doctorDto.getPerson());

            return Optional
                    .ofNullable(doctorDto)
                    .map(dto -> doctorMapper.doctorDtoToEntity(doctorDto, personEntity))
                    .map(entity -> doctorRepository.save(entity))
                    .map(entity -> doctorMapper.entityMessageDto(entity))
                    .orElse(new EntityMessageDto());
        }
        /* -si existe la persona, buscamos por dni y guardamos la guaramos en personEntity
        * de manera tal que no se repitan las mismas personas en diferentes tablas
        * -PersonEntity hace referencia al id de persona en la tabla "doctor" */
            PersonEntity personEntity = personRepository.findByDni(doctorDto.getPerson().getDni());
            return Optional
                    .ofNullable(doctorDto)
                    .map(dto -> doctorMapper.doctorDtoToEntity(doctorDto, personEntity))
                    .map(entity -> doctorRepository.save(entity))
                    .map(entity -> doctorMapper.entityMessageDto(entity))
                    .orElse(new EntityMessageDto());
        }
    }