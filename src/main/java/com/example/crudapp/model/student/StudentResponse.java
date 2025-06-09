package com.example.crudapp.model.student;

import com.example.crudapp.model.department.DepartmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String fullName;
    private String email;
    private Integer age;
    private Boolean isActive;
    private LocalDate enrollmentDate;
    private DepartmentResponse department;
}