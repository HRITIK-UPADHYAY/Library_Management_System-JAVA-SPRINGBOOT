package com.backend.LibraryManagementSystem.Controller;

import com.backend.LibraryManagementSystem.DTO.IssueBookRequestDTO;
import com.backend.LibraryManagementSystem.DTO.IssueBookResponseDTO;
import com.backend.LibraryManagementSystem.Entity.Transaction;
import com.backend.LibraryManagementSystem.Service.TransactionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDTO issueBookRequestDTO) throws Exception {
        IssueBookResponseDTO issueBookResponseDTO;
        try {
            issueBookResponseDTO = transactionService.issueBook(issueBookRequestDTO);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(issueBookResponseDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get")
    public String getAllTransaction(@RequestParam("cardId") int cardId){
        return transactionService.getAllTransaction(cardId);
    }
}
