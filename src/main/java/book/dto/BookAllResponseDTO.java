package book.dto;

import book.entity.Books;
import common.Tag;
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