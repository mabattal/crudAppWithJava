package com.example.crudapp.controller;

import com.example.crudapp.model.student.StudentCreateRequest;
import com.example.crudapp.model.student.StudentCreateResponse;
import com.example.crudapp.model.student.StudentResponse;
import com.example.crudapp.model.student.StudentUpdateRequest;
import com.example.crudapp.service.StudentService;
import com.example.crudapp.service.common.ServiceResult;
import com.example.crudapp.service.common.ServiceResultVoid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentCreateRequest request) {
        ServiceResult<StudentCreateResponse> result = studentService.create(request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ServiceResult<StudentResponse> result = studentService.getById(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAll() {
        ServiceResult<List<StudentResponse>> result = studentService.getAll();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResultVoid> update(@PathVariable Long id, @RequestBody StudentUpdateRequest request) {
        ServiceResultVoid result = studentService.update(id, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ServiceResultVoid result = studentService.delete(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/active/by-name")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getByFullNameAndIsActive(@RequestParam String fullName) {
        ServiceResult<List<StudentResponse>> result = studentService.getByFullName(fullName);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/by-department")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getByDepartmentName(@RequestParam String departmentName) {
        ServiceResult<List<StudentResponse>> result = studentService.getByDepartmentName(departmentName);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/active/paged")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getActiveStudentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ServiceResult<List<StudentResponse>> result = studentService.getActiveStudentsWithPagination(page, size);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
