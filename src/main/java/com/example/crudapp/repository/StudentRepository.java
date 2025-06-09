package com.example.crudapp.repository;

import com.example.crudapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // JpaRepository, CRUD işlemleri için gerekli temel metodları sağlar
    // Ek olarak Student'a özel metodlar buraya eklenebilir

    List<Student> findByFullName(String fullName);

}
