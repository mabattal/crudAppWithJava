package com.example.crudapp.controller;

import com.example.crudapp.model.StudentCreateRequest;
import com.example.crudapp.model.StudentCreateResponse;
import com.example.crudapp.model.StudentResponseDto;
import com.example.crudapp.model.StudentUpdateRequest;
import com.example.crudapp.service.ServiceResult;
import com.example.crudapp.service.ServiceResultVoid;
import com.example.crudapp.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentCreateRequest request) {
        ServiceResult<StudentCreateResponse> result = studentService.create(request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody StudentUpdateRequest request) {
        ServiceResultVoid result = studentService.update(id, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ServiceResult<List<StudentResponseDto>> result = studentService.getAll();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ServiceResult<StudentResponseDto> result = studentService.getById(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ServiceResultVoid result = studentService.delete(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
