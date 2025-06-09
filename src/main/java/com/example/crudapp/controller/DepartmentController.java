package com.example.crudapp.controller;

import com.example.crudapp.model.department.DepartmentCreateRequest;
import com.example.crudapp.model.department.DepartmentCreateResponse;
import com.example.crudapp.model.department.DepartmentResponse;
import com.example.crudapp.model.department.DepartmentUpdateRequest;
import com.example.crudapp.service.DepartmentService;
import com.example.crudapp.service.common.ServiceResult;
import com.example.crudapp.service.common.ServiceResultVoid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // CREATE
    @PostMapping
    public ResponseEntity<ServiceResult<DepartmentCreateResponse>> create(@RequestBody DepartmentCreateRequest request) {
        ServiceResult<DepartmentCreateResponse> result = departmentService.create(request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<DepartmentResponse>> getById(@PathVariable Long id) {
        ServiceResult<DepartmentResponse> result = departmentService.getById(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ServiceResult<List<DepartmentResponse>>> getAll() {
        ServiceResult<List<DepartmentResponse>> result = departmentService.getAll();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResultVoid> update(@PathVariable Long id, @RequestBody DepartmentUpdateRequest request) {
        ServiceResultVoid result = departmentService.update(id, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResultVoid> delete(@PathVariable Long id) {
        ServiceResultVoid result = departmentService.delete(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
