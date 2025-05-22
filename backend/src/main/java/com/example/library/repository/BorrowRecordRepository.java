package com.example.library.repository;

import com.example.library.entity.BorrowRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BorrowRecordRepository extends CrudRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserId(Long userId);
    List<BorrowRecord> findByBookIdAndReturnTimeIsNull(Long bookId);
}
