package com.example.crudapp.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
    private String fullName;
    private String phone;
}
