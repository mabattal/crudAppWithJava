package com.example.crudapp.service;

import com.example.crudapp.entity.Student;
import com.example.crudapp.model.StudentCreateRequest;
import com.example.crudapp.model.StudentCreateResponse;
import com.example.crudapp.model.StudentResponse;
import com.example.crudapp.model.StudentUpdateRequest;
import com.example.crudapp.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ServiceResult<StudentCreateResponse> create(StudentCreateRequest request) {
        Student saved = studentRepository.save(new Student(null, request.getFullName(), request.getPhone()));
        return ServiceResult.success(new StudentCreateResponse(saved.getId()));
    }

    public ServiceResultVoid update(int id, StudentUpdateRequest request) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return ServiceResultVoid.fail("Student with id " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }

        Student student = optionalStudent.get();
        student.setFullName(request.getFullName());
        student.setPhone(request.getPhone());

        studentRepository.save(student);
        return ServiceResultVoid.success();
    }

    public ServiceResult<List<StudentResponse>> getAll() {
        List<Student> students = studentRepository.findAll();
        if(students.isEmpty()){
            return ServiceResult.fail("There is no student", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> dtos = students.stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getFullName(),
                        student.getPhone()
                )).toList();

        return ServiceResult.success(dtos);
    }

    public ServiceResult<StudentResponse> getById(int id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()){
            return ServiceResult.fail("Student not found.", HttpStatus.NOT_FOUND);
        }

        Student student = optionalStudent.get();
        StudentResponse dto = new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getPhone()
        );

        return ServiceResult.success(dto);
    }

    public ServiceResult<List<StudentResponse>> getByFullName(String fullName) {
        List<Student> students = studentRepository.findByFullName(fullName);
        if(students.isEmpty()){
            return ServiceResult.fail("Student not found.", HttpStatus.NOT_FOUND);
        }

        List<StudentResponse> dtos = students.stream().map(student -> new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getPhone()
        )).toList();

        return ServiceResult.success(dtos);
    }

    public ServiceResultVoid delete(int id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()){
            return ServiceResultVoid.fail("Student not found.", HttpStatus.NOT_FOUND);
        }
        studentRepository.deleteById(id);
        return ServiceResultVoid.success();
    }
}
