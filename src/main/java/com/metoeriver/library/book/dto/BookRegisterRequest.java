package com.metoeriver.library.book.dto;

import com.metoeriver.library.book.entity.Books;
import com.metoeriver.library.common.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record BookRegisterRequest(
    @NotBlank String title,
    @NotBlank String author,
    @NotBlank String description,
    @NotNull LocalDate publishedDate,
    @NotNull Tag tag
){
    public BookRegisterRequest toEntity(Books book) {
        return new BookRegisterRequest(
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPublishDate(),
                book.getTag()
        );
    }

}

