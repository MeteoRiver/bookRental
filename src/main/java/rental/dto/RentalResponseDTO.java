package rental.dto;

import book.entity.Books;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import rental.entity.Rental;
import user.entity.Users;

@Getter
@Setter
public class RentalResponseDTO {
    private Books book;
    private Users user;
    private LocalDateTime rentalDate;
    private LocalDateTime  returnDate;
    private boolean returned;

    public static RentalResponseDTO fromEntity(Rental rental) {
        RentalResponseDTO rentalResponseDTO = new RentalResponseDTO();
        rentalResponseDTO.book = rental.getBook();
        rentalResponseDTO.user = rental.getUser();
        rentalResponseDTO.rentalDate = rental.getRentalDate();
        rentalResponseDTO.returnDate = rental.getReturnDate();
        rentalResponseDTO.returned = rental.isReturned();
        return rentalResponseDTO;
    }
}
