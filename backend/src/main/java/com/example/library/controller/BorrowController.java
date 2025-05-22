package com.example.library.controller;

import com.example.library.entity.BorrowRecord;
import com.example.library.service.BorrowService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestParam Long userId, @RequestParam Long bookId) {
        String result = borrowService.borrowBook(userId, bookId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        String result = borrowService.returnBook(userId, bookId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/records/{userId}")
    public ResponseEntity<?> getBorrowRecordsByUser(@PathVariable Long userId) {
        List<BorrowRecord> records = borrowService.getRecordsByUserId(userId);
        if (records.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(records);
    }
}
