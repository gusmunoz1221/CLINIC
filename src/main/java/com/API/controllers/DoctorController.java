package com.API.controllers;

import com.API.Model.Dtos.DoctorDto;
import com.API.Model.Dtos.EntityMessageDto;
import com.API.services.DoctorService;
import com.API.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService, PersonService personService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @PostMapping
    public ResponseEntity<EntityMessageDto> SetDoctor(@RequestBody @Validated DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.addDoctor(doctorDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(doctorService.deleteDoctorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> modofyDoctor(@RequestBody DoctorDto doctorDto, @PathVariable int id) {
        return ResponseEntity.ok(doctorService.modifyDoctor(doctorDto,id));
    }
}
