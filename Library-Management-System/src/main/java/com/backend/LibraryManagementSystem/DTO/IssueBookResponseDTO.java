package com.backend.LibraryManagementSystem.DTO;

import com.backend.LibraryManagementSystem.Entity.Transaction;
import com.backend.LibraryManagementSystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueBookResponseDTO {
    private String trnasactionId;
    private String bookName;
    private TransactionStatus transactionStatus;
}
