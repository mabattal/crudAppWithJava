package com.example.crudapp.model.student;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
    private String fullName;
    private String email;
    private Integer age;
    private Boolean isActive;
    private LocalDate enrollmentDate;
    private Long departmentId;
}
