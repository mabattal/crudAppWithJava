package com.example.crudapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private Integer age;
    private Boolean isActive;
    private LocalDate enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "department_id") // foreign key column
    private Department department;
}
