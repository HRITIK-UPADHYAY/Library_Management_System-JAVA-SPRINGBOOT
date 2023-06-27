package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.BookRequestDTO;
import com.backend.LibraryManagementSystem.DTO.BookResponseDTO;
import com.backend.LibraryManagementSystem.Entity.Author;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Repository.AuthorRepository;
import com.backend.LibraryManagementSystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;

    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO)  {
      //convert the DTO into Entity.
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId()).get();
       Book book = new Book();
       book.setTitle(bookRequestDTO.getTitle());
       book.setPrice(bookRequestDTO.getPrice());
       book.setGenre(bookRequestDTO.getGenre());
       book.setIssued(false);
       book.setAuthor(author);

       List<Book> books = author.getBookList();
       books.add(book);

       authorRepository.save(author); //will save both book and author.

       //Convert the Entity into DTO.
       BookResponseDTO bookResponseDTO = new BookResponseDTO();
       bookResponseDTO.setTitle(book.getTitle());
       bookResponseDTO.setPrice(book.getPrice());

       return bookResponseDTO;
    }



}
