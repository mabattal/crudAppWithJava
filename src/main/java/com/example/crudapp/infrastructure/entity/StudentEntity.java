package com.example.crudapp.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String phone;
}
