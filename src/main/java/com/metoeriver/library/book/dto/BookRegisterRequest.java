package com.metoeriver.library.book.dto;

import com.metoeriver.library.book.entity.Books;
import com.metoeriver.library.book.entity.Tags;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record BookRegisterRequest(
    @NotBlank String title,
    @NotBlank String author,
    @NotBlank String description,
    @NotNull LocalDate publishedDate,
    @NotNull Set<String> tags
){
    public BookRegisterRequest toEntity(Books book) {
        return new BookRegisterRequest(
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPublishDate(),
                book.getTags().stream().map(Tags::getName).collect(Collectors.toSet())
        );
    }

}

