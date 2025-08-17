package book.repository;

import book.entity.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Books, Long> {
    Optional<Books> findByTitle(String title);
    Optional<Books> findByAuthor(String author);

    Page<Books> findAllPageable(Pageable pageable);
    Page<Books> findByTitlePageable(String title, Pageable pageable);
    Page<Books> findByAuthorPageable(String author, Pageable pageable);

}
