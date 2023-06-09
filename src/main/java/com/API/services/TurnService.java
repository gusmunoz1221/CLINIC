package com.API.services;

import com.API.Exceptions.NotFoundException;
import com.API.Exceptions.ValidationException;
import com.API.Model.Dtos.TurnDto;
import com.API.Model.Dtos.TurnDtoResponse;
import com.API.Model.Entities.TurnEntity;
import com.API.Model.mappers.TurnMapper;
import com.API.Model.repositories.DoctorRepository;
import com.API.Model.repositories.PatientRepository;
import com.API.Model.repositories.TurnRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TurnService{

    private final TurnMapper turnMapper;
    private final TurnRepository turnRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public TurnService(TurnMapper turnMapper, TurnRepository turnRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.turnMapper = turnMapper;
        this.turnRepository = turnRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /*recibe un DTO DE TURNOS y devuelve su DTORESPONSE*/
    public TurnDtoResponse addTurn(TurnDto turnDto){
        if(!doctorRepository.existsById(turnDto.getIddoctor()))
            throw new ValidationException("error. el id del medico no existe. ");
        if(!patientRepository.existsById(turnDto.getIdpatient()))
            throw new ValidationException("error. el id del paciente no existe. ");

        return Optional
                .ofNullable(turnDto)
                .map(dto -> turnMapper.turnDtoToEntity(turnDto))
                .map(entity -> turnRepository.save(entity))
                .map(entity -> turnMapper.turnEntityToDto(entity))
                .orElse(new TurnDtoResponse());
    }

    public TurnDtoResponse findTurnById(int id){
        return turnRepository.findById(id)
                             .map(turnMapper::turnEntityToDto)
                             .orElse(null);
    }

    public List<TurnDtoResponse> findAllTurns(){
        return turnRepository.findAll()
                             .stream()
                             .map(turnMapper::turnEntityToDto)
                             .toList();
    }

    public boolean deleteTurnById(int id){
        if(!turnRepository.existsById(id))
            throw new NotFoundException("no se encontro el paciente que desea eliminar");
        turnRepository.deleteById(id);
        return true;
    }

    public TurnDtoResponse turnModify(TurnDto turnDto, Integer id){
        if(turnRepository.existsById(id))
            throw new NotFoundException("No se encontro el turno que desea modificar.");
        if(doctorRepository.existsById(turnDto.getIddoctor()))
            throw new ValidationException("error. el id del medico no existe. ");
        if(patientRepository.existsById(turnDto.getIdpatient()))
            throw new ValidationException("error. el id del paciente no existe.");

        return Optional
                .ofNullable(turnDto)
                .map(dto -> turnMapper.turnDtoToEntity(turnDto))
                .map(entity -> turnRepository.save(entity))
                .map(entity -> turnMapper.turnEntityToDto(entity))
                .orElse(new TurnDtoResponse());
    }
}

