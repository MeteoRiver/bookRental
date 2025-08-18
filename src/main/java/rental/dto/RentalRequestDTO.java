package rental.dto;

import book.entity.Books;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import user.entity.Users;

public record RentalRequestDTO(
        Long id,
        Books book,
        Users user,
        LocalDateTime rentalDate,
        LocalDateTime returnDate,
        boolean returned
) {
}
