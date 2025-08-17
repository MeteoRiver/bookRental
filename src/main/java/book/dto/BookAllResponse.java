package book.dto;

import book.entity.Books;
import common.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookAllResponse {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Date publishDate;
    private Tag tag;

    public static BookAllResponse fromEntity(Books book) {
        BookAllResponse bookAllResponse = new BookAllResponse();
        bookAllResponse.id = book.getId();
        bookAllResponse.title = book.getTitle();
        bookAllResponse.author = book.getAuthor();
        bookAllResponse.description = book.getDescription();
        bookAllResponse.publishDate = book.getPublishDate();
        bookAllResponse.tag = book.getTag();
        return bookAllResponse;
    }

}