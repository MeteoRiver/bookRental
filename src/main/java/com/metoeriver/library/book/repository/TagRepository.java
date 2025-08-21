package com.metoeriver.library.book.repository;

import com.metoeriver.library.book.entity.Tags;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tags, String> {
    Optional<Tags> findByNameIgnoreCase(String name);
}
