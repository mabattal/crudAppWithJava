package com.example.crudapp.repository;

import com.example.crudapp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.synth.Region;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // JpaRepository, CRUD işlemleri için gerekli temel metodları sağlar
    // Ek olarak Student'a özel metodlar buraya eklenebilir


    //İlişkili tablo alanı ile ilgili sorgu
    List<Student> findByDepartment_Name(String departmentName);

    //çoklu şart
    List<Student> findByFullNameAndIsActiveTrue(String fullName);

    //Boolen kontrol
    boolean existsByEmail(String email);

    //Sayfalama ile
    Page<Student> findByIsActiveTrue(Pageable pageable);




    //Sıralı liste
    List<Student> findByIsActiveTrueOrderByEnrollmentDateDesc();

    //ilk kayıt
    Student findFirstByOrderByEnrollmentDateAsc();

    //top N kayıt
    List<Student> findTop3ByAgeGreaterThanOrderByAgeDesc(int age);

    //liste parametreli
    List<Student> findByAgeIn(List<Integer> ages);

    //Boş alan kontrolü
    List<Student> findByEmailIsNull();

    //yaş aralığı
    List<Student> findByAgeBetween(int minAge, int maxAge);

    //Tarih kontrolü
    List<Student> findByEnrollmentDateBefore(LocalDate date);

    //basit filtreleme
    Optional<Student> findByEmail(String email);

}
