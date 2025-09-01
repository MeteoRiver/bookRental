package com.metoeriver.library.book.dto;

import com.metoeriver.library.book.entity.Books;
import com.metoeriver.library.book.entity.Tags;
import java.time.LocalDate;
import java.util.List;
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
    private List<String> tag;

    public static BookAllResponseDTO fromEntity(Books book) {
        BookAllResponseDTO bookAllResponseDTO = new BookAllResponseDTO();
        bookAllResponseDTO.id = book.getId();
        bookAllResponseDTO.title = book.getTitle();
        bookAllResponseDTO.author = book.getAuthor();
        bookAllResponseDTO.description = book.getDescription();
        bookAllResponseDTO.publishDate = book.getPublishDate();
        bookAllResponseDTO.tag = book.getTags().stream().map(Tags::getName).toList();
        return bookAllResponseDTO;
    }

}