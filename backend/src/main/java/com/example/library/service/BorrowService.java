package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BorrowRecord;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    public String borrowBook(Long userId, Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            return "图书不存在";
        }

        Book book = optionalBook.get();
        if (book.getStock() <= 0) {
            return "库存不足";
        }

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowTime(LocalDateTime.now());
        borrowRecordRepository.save(record);

        return "借书成功";
    }

    public String returnBook(Long userId, Long bookId) {
        List<BorrowRecord> records = borrowRecordRepository.findByBookIdAndReturnTimeIsNull(bookId);
        for (BorrowRecord record : records) {
            if (record.getUserId().equals(userId)) {
                record.setReturnTime(LocalDateTime.now());
                borrowRecordRepository.save(record);

                bookRepository.findById(bookId).ifPresent(book -> {
                    book.setStock(book.getStock() + 1);
                    bookRepository.save(book);
                });
                return "还书成功";
            }
        }
        return "未找到未归还记录";
    }

    public List<BorrowRecord> getRecordsByUserId(Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }
}