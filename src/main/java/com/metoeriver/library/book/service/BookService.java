package com.metoeriver.library.book.service;

import book.dto.*;
import com.metoeriver.library.book.dto.BookAllRequest;
import com.metoeriver.library.book.dto.BookAllResponseDTO;
import com.metoeriver.library.book.dto.BookDeleteResponse;
import com.metoeriver.library.book.dto.BookRegisterRequest;
import com.metoeriver.library.book.entity.Books;
import com.metoeriver.library.book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookAllResponseDTO createBook(BookRegisterRequest bookRegisterRequest) {
        Books save = new Books();
        save.setTitle(bookRegisterRequest.title());
        save.setAuthor(bookRegisterRequest.author());
        save.setDescription(bookRegisterRequest.description());
        save.setTag(bookRegisterRequest.tag());
        save.setPublishDate(bookRegisterRequest.publishedDate());

        Books saved = bookRepository.save(save);

        return BookAllResponseDTO.fromEntity(saved);
    }

    public BookAllResponseDTO updateBook(BookAllRequest bookAllRequest) {

        Books save = bookRepository.findById(bookAllRequest.id()).orElseThrow(()->new RuntimeException("ERROR수정"));

        save.setTitle(bookAllRequest.title());
        save.setAuthor(bookAllRequest.author());
        save.setDescription(bookAllRequest.description());
        save.setTag(bookAllRequest.tag());
        save.setPublishDate(bookAllRequest.publishedDate());

        Books updated = bookRepository.save(save);

        return BookAllResponseDTO.fromEntity(updated);

    }

    @Transactional(readOnly = true)
    public Page<BookAllResponseDTO> readAllBook(Pageable pageable) {
        return bookRepository.findAllPageable(pageable)
                .map(BookAllResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public BookAllResponseDTO readOneBook(Long id){
        return bookRepository.findById(id)
                .map(BookAllResponseDTO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("에러"));
    }

    @Transactional(readOnly = true)
    public Page<BookAllResponseDTO> readOneBookByTitle(String title, Pageable pageable){
        return bookRepository.findByTitlePageable(title, pageable)
                .map(BookAllResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<BookAllResponseDTO> readOneBookByAuthor(String author, Pageable pageable){
        return bookRepository.findByAuthorPageable(author, pageable)
            .map(BookAllResponseDTO::fromEntity);
    }

    public BookDeleteResponse deleteBook(Long id) {
        if(!bookRepository.existsById(id)){
            throw new EntityNotFoundException("에러코드 명시");
        }
        bookRepository.deleteById(id);
        BookDeleteResponse bookDeleteResponse = new BookDeleteResponse();
        bookDeleteResponse.setResult(!bookRepository.existsById(id));
        return bookDeleteResponse;
    }
}
