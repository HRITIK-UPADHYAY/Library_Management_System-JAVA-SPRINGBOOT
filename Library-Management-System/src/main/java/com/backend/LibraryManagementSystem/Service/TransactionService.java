package com.backend.LibraryManagementSystem.Service;

import com.backend.LibraryManagementSystem.DTO.IssueBookRequestDTO;
import com.backend.LibraryManagementSystem.DTO.IssueBookResponseDTO;
import com.backend.LibraryManagementSystem.Entity.Book;
import com.backend.LibraryManagementSystem.Entity.LibraryCard;
import com.backend.LibraryManagementSystem.Entity.Transaction;
import com.backend.LibraryManagementSystem.Enum.CardStatus;
import com.backend.LibraryManagementSystem.Enum.TransactionStatus;
import com.backend.LibraryManagementSystem.Repository.BookRepository;
import com.backend.LibraryManagementSystem.Repository.LibraryCardRepository;
import com.backend.LibraryManagementSystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    LibraryCardRepository libraryCardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

//    @Autowired
//    private JavaMailSender emailSender;

    public IssueBookResponseDTO issueBook(IssueBookRequestDTO issueBookRequestDTO) throws Exception {
        //create Transaction Object;
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);  //true means transaction is for issue a book.

        //Get the Book and Card Information.
        LibraryCard card;
        try {
            card = libraryCardRepository.findById(issueBookRequestDTO.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Card Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card Id");
        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDTO.getBookId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book Id");
        }

        //both card and book are valid if none throw exception.
        transaction.setBook(book); //set the book in transaction class.
        transaction.setCard(card); //set the card in transaction class.

        //before issue the book i will check two condition.
        //1 -->  cardStatus.
        //2 -->  Book is issued or not.
        if(card.getStatus() != CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your Card Is Not Activated");
            transactionRepository.save(transaction);
            throw new Exception("Your Card Is Not Activated");
        }
        if(book.isIssued()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! Book Is Already Issued");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! Book Is Already Issued");
        }

        //now I can Issue the Book.
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction Successfull");

        book.setIssued(true);   //for parent-child relationship.
        book.setCard(card);
        book.getTransaction().add(transaction);

        card.getTransactionList().add(transaction); //for parent-child relationship.
        card.getBooksIssued().add(book);

        libraryCardRepository.save(card); //will save all card,book and transaction class.

        //Response DTO
        IssueBookResponseDTO issueBookResponseDTO = new IssueBookResponseDTO();
        issueBookResponseDTO.setTrnasactionId(transaction.getTransactionNumber());
        issueBookResponseDTO.setTransactionStatus(TransactionStatus.SUCCESS);
        issueBookResponseDTO.setBookName(book.getTitle());

//        //step to send a email.
//        String text = "Congrats !!. " + card.getStudent().getName() + " You Have Been Issued" + book.getTitle() + "Book";
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("librarymanagementsystembackend@gmail.com");
//        message.setTo(card.getStudent().getEmail());
//        message.setSubject("Issue Book Notification");
//        message.setText(text);
//        emailSender.send(message);

        return issueBookResponseDTO;
    }

    public String getAllTransaction(int cardId){
      List<Transaction> transactionList = transactionRepository.getAllSUccesfullTransactionWithCardNo(cardId);
        String ans = "";
        for(Transaction t : transactionList){
            ans += t.getTransactionNumber();
            ans += "\n";
        }

        return ans;
    }
}
