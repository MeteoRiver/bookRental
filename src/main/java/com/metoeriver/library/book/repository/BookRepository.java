package com.metoeriver.library.book.repository;

import com.metoeriver.library.book.entity.Books;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Books, Long> {
    Optional<Books> findByTitle(String title);
    Optional<Books> findByAuthor(String author);

    Page<Books> findAllPageable(Pageable pageable);
    Page<Books> findByTitlePageable(String title, Pageable pageable);
    Page<Books> findByAuthorPageable(String author, Pageable pageable);


    // ANY: 주어진 태그 중 하나라도 포함
    @Query("""
        select distinct b
        from Books b
        join b.tags t
        where lower(t.name) in :names
        """)
    Page<Books> findAnyByTagNames(@Param("names") Collection<String> names, Pageable pageable);

    // ALL: 주어진 태그를 모두 포함
    @Query("""
        select b
        from Books b
        join b.tags t
        where lower(t.name) in :names
        group by b
        having count(distinct lower(t.name)) = :size
        """)
    Page<Books> findAllByTagNames(@Param("names") Collection<String> names,
                                 @Param("size") long size,
                                 Pageable pageable);
}
