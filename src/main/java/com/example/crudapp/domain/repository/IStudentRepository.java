package com.example.crudapp.domain.repository;

import com.example.crudapp.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    Student save(Student student);                  // create & update
    Optional<Student> findById(int id);             // read one
    List<Student> findAll();                        // read all
    void deleteById(int id);                        // delete
    boolean existsById(int id);                     // var mı kontrolü
}
