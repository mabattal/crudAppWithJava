package com.example.crudapp.infrastructure.persistence;

import com.example.crudapp.infrastructure.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataStudentRepository extends JpaRepository<StudentEntity, Integer> {
}
