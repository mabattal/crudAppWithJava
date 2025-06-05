package com.example.crudapp.repository;

import com.example.crudapp.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final SpringDataStudentRepository springRepository;

    public StudentRepository(SpringDataStudentRepository springRepository) {
        this.springRepository = springRepository;
    }

    public Student save(Student student) {
        return springRepository.save(student);
    }

    public Optional<Student> findById(int id) {
        return springRepository.findById(id);
    }

    public List<Student> findAll() {
        return springRepository.findAll();
    }

    public void deleteById(int id) {
        springRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return springRepository.existsById(id);
    }
}
