package com.API.services;

import com.API.Exceptions.BadRequestException;
import com.API.Exceptions.NotFoundException;
import com.API.Exceptions.ValidationException;
import com.API.Model.Dtos.DoctorDto;
import com.API.Model.Dtos.EntityMessageDto;
import com.API.Model.Entities.PersonEntity;
import com.API.Model.mappers.DoctorMapper;
import com.API.Model.mappers.PersonMapper;
import com.API.Model.repositories.DoctorRepository;
import com.API.Model.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PersonService personService;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper, PersonRepository personRepository, PersonMapper personMapper, PersonService personService) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.personService = personService;
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
                throw new ValidationException("No se cargaron los datos de la persona...");
        if (doctorDto.getConsultation() < 0)
            throw new ValidationException("La consulta no peude ser menor a 0");
        if (doctorDto.getProfession().equals(null))
            throw new ValidationException("la profesion ingresada es incorrecta");

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

        public boolean deleteDoctorById(int id){
            if(!doctorRepository.existsById(id))
                throw new NotFoundException("no se encontro el medico que desea eliminar");
           doctorRepository.deleteById(id);
           return true;
        }

        public DoctorDto modifyDoctor(DoctorDto doctorDto, int id) {
            if (doctorDto.getPerson() == null)
                throw new BadRequestException("No se cargaron los datos de la persona...");
            if (!doctorRepository.existsById(id))
                throw new ValidationException("el doctor con id:" + id + " no existe en la base de datos..");
            if (doctorDto.getConsultation() < 0)
                throw new BadRequestException("La consulta no peude ser menor a 0");
            if (doctorDto.getProfession().equals(null))
                throw new BadRequestException("la profesion ingresada es incorrecta");

            //busco el id de la tabla persona que lleva el doctor actualmente y lo seteo para sobreescribir los datos
                int idPerson = doctorRepository.findById(id).get().getPersonEntity().getId();
                PersonEntity personEntity = personMapper.personDtoToEntity(doctorDto.getPerson());
                personEntity.setId(idPerson);
                personRepository.save(personEntity);

                return Optional
                        .ofNullable(doctorDto)
                        .map(dto -> doctorMapper.doctorDtoToEntityModify(doctorDto,personEntity,id))
                        .map(entity -> doctorRepository.save(entity))
                        .map(entity -> doctorMapper.doctortEntityToDto(entity))
                        .orElse(new DoctorDto());
    }

        /*--------------PAGINACION-------------*/
    // recibo cantida de paginas, el tamaño, y las ordeno por id
    public Page<DoctorDto> listAllByPage(Integer pageNumber, Integer pageSize, String orderFiel) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderFiel));
        return doctorRepository.findAll(pageable).map(doctorMapper::doctortEntityToDto);
    }

    public Page<DoctorDto> listPageById(int id ,Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return doctorRepository.findById(id,pageable);
    }
}