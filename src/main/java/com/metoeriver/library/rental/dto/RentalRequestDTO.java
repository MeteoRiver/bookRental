package com.metoeriver.library.rental.dto;

import com.metoeriver.library.book.entity.Books;
import java.time.LocalDateTime;
import com.metoeriver.library.user.entity.Users;

public record RentalRequestDTO(
        Long id,
        Books book,
        Users user,
        LocalDateTime rentalDate,
        LocalDateTime returnDate,
        boolean returned
) {
}
