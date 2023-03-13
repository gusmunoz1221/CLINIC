package com.API.controllers;

import com.API.Model.Dtos.DoctorDto;
import com.API.Model.Dtos.EntityMessageDto;
import com.API.services.DoctorService;
import com.API.services.PersonService;
import org.springframework.data.domain.Page;
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

    /*PAGINACION*/

    //lista todos ordenados por id --> se recibe por parametro
    @GetMapping("/page")
    public ResponseEntity<Page<DoctorDto>> listByPage
            (
                    @RequestParam(name = "pageNumber",defaultValue = "0") Integer pageNumber,
                    @RequestParam(name = "pageSize",defaultValue = "2") Integer pageSize,
                    @RequestParam(name = "orderFile",defaultValue = "id") String orderFiel
            ){
        return ResponseEntity.ok(doctorService.listAllByPage(pageNumber,pageSize,orderFiel));
    }

    @GetMapping("/page/{id}")
    public  ResponseEntity<Page<DoctorDto>>listOneByPage
            (
                    @PathVariable int id,
                    @RequestParam(name = "pageNumber",defaultValue = "0") Integer pageNumber,
                    @RequestParam(name = "pageSize",defaultValue = "1") Integer pageSize
            ){
        return  ResponseEntity.ok(doctorService.listPageById(id,pageNumber,pageSize));
    }
}
