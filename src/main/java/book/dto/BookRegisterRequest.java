package book.dto;

import book.entity.Books;
import common.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public record BookRegisterRequest(
    @NotBlank String title,
    @NotBlank String author,
    @NotBlank String description,
    @NotNull Date publishedDate,
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

