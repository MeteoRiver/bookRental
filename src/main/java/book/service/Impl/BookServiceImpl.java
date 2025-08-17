package book.service.Impl;

import book.dto.*;
import book.entity.Books;
import book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl {
    @Autowired
    private BookRepository bookRepository;

    public BookAllResponse createBook(BookRegisterRequest bookRegisterRequest) {
        Books save = new Books();
        save.setTitle(bookRegisterRequest.title());
        save.setAuthor(bookRegisterRequest.author());
        save.setDescription(bookRegisterRequest.description());
        save.setTag(bookRegisterRequest.tag());
        save.setPublishDate(bookRegisterRequest.publishedDate());

        Books saved = bookRepository.save(save);

        return BookAllResponse.fromEntity(saved);
    }

    public BookAllResponse updateBook(BookAllRequest bookAllRequest) {

        Books save = bookRepository.findById(bookAllRequest.id()).orElseThrow(()->new RuntimeException("ERROR수정"));

        save.setTitle(bookAllRequest.title());
        save.setAuthor(bookAllRequest.author());
        save.setDescription(bookAllRequest.description());
        save.setTag(bookAllRequest.tag());
        save.setPublishDate(bookAllRequest.publishedDate());

        Books updated = bookRepository.save(save);

        return BookAllResponse.fromEntity(updated);

    }

    @Transactional(readOnly = true)
    public Page<BookAllResponse> readAllBook(Pageable pageable) {
        return bookRepository.findAllPageable(pageable)
                .map(BookAllResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public BookAllResponse readOneBook(Long id){
        return bookRepository.findById(id)
                .map(BookAllResponse::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("에러"));
    }

    @Transactional(readOnly = true)
    public Page<BookAllResponse> readOneBookByTitle(String title, Pageable pageable){
        return bookRepository.findByTitlePageable(title, pageable)
                .map(BookAllResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<BookAllResponse> readOneBookByAuthor(String author, Pageable pageable){
        return bookRepository.findByAuthorPageable(author, pageable)
            .map(BookAllResponse::fromEntity);
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
