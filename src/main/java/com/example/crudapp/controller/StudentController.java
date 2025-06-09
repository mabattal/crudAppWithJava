package com.example.crudapp.controller;

import com.example.crudapp.model.student.StudentCreateRequest;
import com.example.crudapp.model.student.StudentCreateResponse;
import com.example.crudapp.model.student.StudentResponse;
import com.example.crudapp.model.student.StudentUpdateRequest;
import com.example.crudapp.service.StudentService;
import com.example.crudapp.service.common.ServiceResult;
import com.example.crudapp.service.common.ServiceResultVoid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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


    @GetMapping("/by-email")
    public ResponseEntity<ServiceResult<StudentResponse>> getByEmail(@RequestParam String email) {
        ServiceResult<StudentResponse> result = studentService.getByEmail(email);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/active/by-name")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getByFullNameAndIsActive(@RequestParam String fullName) {
        ServiceResult<List<StudentResponse>> result = studentService.getByFullName(fullName);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/age-range")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getByAgeBetween(@RequestParam int min, @RequestParam int max) {
        ServiceResult<List<StudentResponse>> result = studentService.getByAgeRange(min, max);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/active/latest")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getActiveOrderedByEnrollmentDateDesc() {
        ServiceResult<List<StudentResponse>> result = studentService.getActiveStudentsOrderedByEnrollmentDate();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/first-enrolled")
    public ResponseEntity<ServiceResult<StudentResponse>> getFirstEnrolled() {
        ServiceResult<StudentResponse> result = studentService.getFirstStudentByEnrollmentDate();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/top3-age")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getTop3OlderThan(@RequestParam int age) {
        ServiceResult<List<StudentResponse>> result = studentService.getTop3StudentsByAge(age);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/by-ages")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getByAgeIn(@RequestParam List<Integer> ages) {
        ServiceResult<List<StudentResponse>> result = studentService.getByAges(ages);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/no-email")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getWithNullEmail() {
        ServiceResult<List<StudentResponse>> result = studentService.getStudentsWithNullEmail();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/by-department")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getByDepartmentName(@RequestParam String departmentName) {
        ServiceResult<List<StudentResponse>> result = studentService.getByDepartmentName(departmentName);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("/before-date")
    public ResponseEntity<ServiceResult<List<StudentResponse>>> getEnrolledBefore(@RequestParam String date) {
        ServiceResult<List<StudentResponse>> result = studentService.getByEnrollmentDateBefore(date);
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
