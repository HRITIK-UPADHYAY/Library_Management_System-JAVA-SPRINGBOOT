package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.AuthorRequestDTO;
import com.backend.LibraryManagementSystem.DTO.AuthorResponseDTO;
import com.backend.LibraryManagementSystem.Entity.Author;
import com.backend.LibraryManagementSystem.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public AuthorResponseDTO addAuthor(@RequestBody AuthorRequestDTO authorRequestDTO){
        return authorService.addAuthor(authorRequestDTO);
    }

    @GetMapping("/get-authors")
    public List<AuthorResponseDTO>  getAuthors(){
        return authorService.getAuthors();
    }
}
