package com.example.crudapp.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateRequest {
    private String fullName;
    private String phone;
}
