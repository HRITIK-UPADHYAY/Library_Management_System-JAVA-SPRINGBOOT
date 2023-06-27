package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.StudentRequestDTO;
import com.backend.LibraryManagementSystem.DTO.StudentResponseDTO;
import com.backend.LibraryManagementSystem.DTO.StudentUpdateEmailRequestDTO;
import com.backend.LibraryManagementSystem.Entity.Student;
import com.backend.LibraryManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public StudentResponseDTO addStudent(@RequestBody StudentRequestDTO studentRequestDTO){
        return studentService.addStudent(studentRequestDTO);
    }

    @GetMapping("/find-by-email")
    public ResponseEntity getStudentByEmail(@RequestParam("email") String email){
        StudentResponseDTO studentResponseDTO;
        try{
            studentResponseDTO = studentService.getStudentByEmail(email);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(studentResponseDTO, HttpStatus.ACCEPTED);
    }


    @GetMapping("find-by-age")
    public ResponseEntity getStudentByAge(@RequestParam("age") int age){
        List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();
        try{
            studentResponseDTOS = studentService.getStudentByAge(age);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(studentResponseDTOS, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-email")
    public StudentResponseDTO updateEmail(@RequestBody StudentUpdateEmailRequestDTO studentUpdateEmailRequestDTO){
       return studentService.updateEmail(studentUpdateEmailRequestDTO);
    }
}
