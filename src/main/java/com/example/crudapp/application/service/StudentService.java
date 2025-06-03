package com.example.crudapp.application.service;

import com.example.crudapp.application.port.IStudentService;
import com.example.crudapp.domain.model.Student;
import com.example.crudapp.domain.repository.IStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(int id, Student student) {
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("Student with id: " + id + " does not exist.");
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public boolean delete(int id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
