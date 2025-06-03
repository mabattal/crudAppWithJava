package com.example.crudapp.web.controller;

import com.example.crudapp.application.port.IStudentService;
import com.example.crudapp.domain.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student){
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student){
        Student updatedStudent = studentService.update(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id){
        Optional<Student> student = studentService.getById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id){
        boolean isDeleted = studentService.delete(id);
        return isDeleted ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }
}
