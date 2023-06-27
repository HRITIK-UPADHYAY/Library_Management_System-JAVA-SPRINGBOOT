package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.BookRequestDTO;
import com.backend.LibraryManagementSystem.DTO.BookResponseDTO;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public BookResponseDTO addBook(@RequestBody BookRequestDTO bookRequestDTO){
       return bookService.addBook(bookRequestDTO);
    }
}
