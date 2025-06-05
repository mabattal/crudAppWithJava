package com.example.crudapp.repository;

import com.example.crudapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataStudentRepository extends JpaRepository<Student, Integer> {
}
