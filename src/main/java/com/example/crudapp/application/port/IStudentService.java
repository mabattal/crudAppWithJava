package com.example.crudapp.application.port;

import com.example.crudapp.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student create(Student student);
    Student update(int id, Student student);
    List<Student> getAll();
    Optional<Student> getById(int id);
    boolean delete(int id);
}
