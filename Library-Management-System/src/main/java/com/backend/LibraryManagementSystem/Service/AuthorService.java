package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.AuthorRequestDTO;
import com.backend.LibraryManagementSystem.DTO.AuthorResponseDTO;
import com.backend.LibraryManagementSystem.Entity.Author;
import com.backend.LibraryManagementSystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorResponseDTO addAuthor(AuthorRequestDTO authorRequestDTO){
        //create a author.
        Author author = new Author();
        author.setName(authorRequestDTO.getName());
        author.setAge(authorRequestDTO.getAge());
        author.setMobNo(authorRequestDTO.getMobNo());
        author.setEmail(authorRequestDTO.getEmail());

        //save the author.
        Author updatedAuthor = authorRepository.save(author);

        //create a AuthorResponseDTO.
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setAuthorId(updatedAuthor.getAuthorId());
        authorResponseDTO.setName(updatedAuthor.getName());
        authorResponseDTO.setAge(updatedAuthor.getAge());
        authorResponseDTO.setMobNo(updatedAuthor.getMobNo());
        authorResponseDTO.setEmail(updatedAuthor.getEmail());

        return authorResponseDTO;
    }

    public List<AuthorResponseDTO> getAuthors(){
        List<Author> authors = authorRepository.findAll();

        List<AuthorResponseDTO> authorResponseDTOS = new ArrayList<>();
        for(Author author : authors){
            AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
            authorResponseDTO.setAuthorId(author.getAuthorId());
            authorResponseDTO.setName(author.getName());
            authorResponseDTO.setAge(author.getAge());
            authorResponseDTO.setMobNo(author.getMobNo());
            authorResponseDTO.setEmail(author.getEmail());

            authorResponseDTOS.add(authorResponseDTO);
        }

        return authorResponseDTOS;
    }
}
