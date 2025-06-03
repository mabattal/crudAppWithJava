package com.example.crudapp.infrastructure.persistence;

import com.example.crudapp.domain.model.Student;
import com.example.crudapp.domain.repository.IStudentRepository;
import com.example.crudapp.infrastructure.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StudentRepository implements IStudentRepository {

    private final SpringDataStudentRepository springRepository;

    public StudentRepository(SpringDataStudentRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Student save(Student student) {
        StudentEntity savedEntity = springRepository.save(toEntity(student));
        return toModel(savedEntity);
    }

    @Override
    public Optional<Student> findById(int id) {
        return springRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<Student> findAll() {
        return springRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) {
        springRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return springRepository.existsById(id);
    }

    // Manual mapping from domain model to entity
    private StudentEntity toEntity(Student student){
        return new StudentEntity(student.getId(), student.getFullName(), student.getPhone());
    }

    // Manual mapping from entity to domain model
    private Student toModel(StudentEntity entity){
        return new Student(entity.getId(), entity.getFullName(), entity.getPhone());
    }
}
