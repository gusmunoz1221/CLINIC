package com.API.controllers;

import com.API.Model.Dtos.EntityMessageDto;
import com.API.Model.Dtos.PatientDto;
import com.API.services.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController{
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(patientService.findPatientById(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @PostMapping
    public ResponseEntity<EntityMessageDto> setPatient(@RequestBody @Validated PatientDto patientDto) {
        return ResponseEntity.ok(patientService.addPatient(patientDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> deletePatient(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(patientService.deletePatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> modofyDoctor(@RequestBody PatientDto patientDto, @PathVariable int id) {
        return ResponseEntity.ok(patientService.modifyPatient(patientDto,id));
    }

    /*PAGINACION*/

    //lista todos ordenados por id --> se recibe por parametro
    @GetMapping("/page")
    public ResponseEntity<Page<PatientDto>> listByPage
    (
            @RequestParam(name = "pageNumber",defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize,
            @RequestParam(name = "orderFile",defaultValue = "id") String orderFiel
    ){
        return ResponseEntity.ok(patientService.listAllByPage(pageNumber,pageSize,orderFiel));
    }

    @GetMapping("/page/{id}")
    public  ResponseEntity<Page<PatientDto>>listOneByPage
            (
                    @PathVariable int id,
                    @RequestParam(name = "pageNumber",defaultValue = "0") Integer pageNumber,
                    @RequestParam(name = "pageSize",defaultValue = "1") Integer pageSize
            ){
        return  ResponseEntity.ok(patientService.listPageById(id,pageNumber,pageSize));
    }
}
