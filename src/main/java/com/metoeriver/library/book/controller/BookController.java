package com.metoeriver.library.book.controller;

import com.metoeriver.library.book.dto.BookAllRequest;
import com.metoeriver.library.book.dto.BookRegisterRequest;
import com.metoeriver.library.book.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookRegisterRequest bookRegisterRequest) {
        return ResponseEntity.ok(bookService.createBook(bookRegisterRequest));
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.readAllBook(pageable));
    }

    @GetMapping
    public ResponseEntity<?> getBookById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(bookService.readOneBook(id));

    }

    @GetMapping
    public ResponseEntity<?> getBookByTitle(@RequestParam("title") String title, Pageable pageable) {
        return ResponseEntity.ok(bookService.readOneBookByTitle(title, pageable));
    }

    @GetMapping
    public ResponseEntity<?> getBookByAuthor(@RequestParam("author") String author, Pageable pageable) {
        return ResponseEntity.ok(bookService.readOneBookByAuthor(author, pageable));
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody BookAllRequest bookAllRequest) {
        return ResponseEntity.ok(bookService.updateBook(bookAllRequest));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBookById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/books")
    public ResponseEntity<?> list(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "any") String mode,
            Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.readByTags(tags, mode, pageable));
    }
}
