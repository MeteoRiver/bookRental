package com.metoeriver.library.book.dto;

import com.metoeriver.library.book.entity.Books;
import com.metoeriver.library.common.Tag;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAllResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private LocalDate publishDate;
    private Tag tag;

    public static BookAllResponseDTO fromEntity(Books book) {
        BookAllResponseDTO bookAllResponseDTO = new BookAllResponseDTO();
        bookAllResponseDTO.id = book.getId();
        bookAllResponseDTO.title = book.getTitle();
        bookAllResponseDTO.author = book.getAuthor();
        bookAllResponseDTO.description = book.getDescription();
        bookAllResponseDTO.publishDate = book.getPublishDate();
        bookAllResponseDTO.tag = book.getTag();
        return bookAllResponseDTO;
    }

}