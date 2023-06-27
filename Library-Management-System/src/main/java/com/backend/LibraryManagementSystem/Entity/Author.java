package com.backend.LibraryManagementSystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;

    private String name;

    private int age;
    private String mobNo;

    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Book> bookList = new ArrayList<>();
}
