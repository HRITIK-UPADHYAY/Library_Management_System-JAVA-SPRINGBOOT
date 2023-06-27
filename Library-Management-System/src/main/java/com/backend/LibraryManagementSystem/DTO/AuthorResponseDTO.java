package com.backend.LibraryManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDTO {
    private int authorId;
    private String name;
    private int age;
    private String mobNo;
    private String email;
}
