package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.StudentRequestDTO;
import com.backend.LibraryManagementSystem.DTO.StudentResponseDTO;
import com.backend.LibraryManagementSystem.DTO.StudentUpdateEmailRequestDTO;
import com.backend.LibraryManagementSystem.Entity.LibraryCard;
import com.backend.LibraryManagementSystem.Entity.Student;
import com.backend.LibraryManagementSystem.Enum.CardStatus;
import com.backend.LibraryManagementSystem.Exception.StudentNotFoundException;
import com.backend.LibraryManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO){
        //convert the DTO into Entity
        //create a student object.
        Student student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setAge(studentRequestDTO.getAge());
        student.setDepartment(studentRequestDTO.getDepartment());
        student.setEmail(studentRequestDTO.getEmail());

        //create a Library Card object.
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student);
       // card.setUpadationDate();

        //set card in student.
        student.setCard(card);

        Student updateStudent = studentRepository.save(student); //will save both student and card.

        //create a StudentResponseDTO.
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(updateStudent.getId());
        studentResponseDTO.setName(updateStudent.getName());
        studentResponseDTO.setEmail(updateStudent.getEmail());

        return studentResponseDTO;

//        //save the card automatically.
//        LibraryCard card = new LibraryCard();
//        card.setValidTill("03/25");
//        card.setStatus(CardStatus.ACTIVATED);
//        card.setStudent(student);
//
//        //for bidirectional mapping we have to save the card in student class.
//        student.setCard(card);
//
//        studentRepository.save(student);
    }

    public StudentResponseDTO getStudentByEmail(String email) throws StudentNotFoundException {
        Student student;
        try {
            student = studentRepository.findByEmail(email);
            if(student.equals(null)) throw new RuntimeException();
        }
        catch (Exception e){
            throw  new StudentNotFoundException("Invalid Email Id");
        }

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setEmail(student.getEmail());

        return studentResponseDTO;
    }

    public List<StudentResponseDTO> getStudentByAge(int age) throws StudentNotFoundException {
        List<Student> students = new ArrayList<>();
        try{
            students = studentRepository.findByAge(age);
            if(students.isEmpty()) throw new RuntimeException();
        }
        catch (Exception e){
            throw new StudentNotFoundException("No Student Is Present IN This Age");
        }

        List<StudentResponseDTO> studentRequestDTOS = new ArrayList<>();
        for(Student student : students){
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
            studentResponseDTO.setId(student.getId());
            studentResponseDTO.setName(student.getName());
            studentResponseDTO.setEmail(student.getEmail());

            studentRequestDTOS.add(studentResponseDTO);
        }

        return studentRequestDTOS;
    }

    public StudentResponseDTO updateEmail(StudentUpdateEmailRequestDTO studentUpdateEmailRequestDTO){
        //convert the DTO into entity.
        Student student = studentRepository.findById(studentUpdateEmailRequestDTO.getId()).get();
        student.setEmail(studentUpdateEmailRequestDTO.getEmail());

        //update step
        Student updatedStudent = studentRepository.save(student);

        //convert the Entity into DTO for return.
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(updatedStudent.getId());
        studentResponseDTO.setName(updatedStudent.getName());
        studentResponseDTO.setEmail(updatedStudent.getEmail());

        return studentResponseDTO;
    }
}
