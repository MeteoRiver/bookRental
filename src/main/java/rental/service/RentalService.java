package rental.service;

import book.entity.Books;
import book.repository.BookRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import rental.dto.RentalRequestDTO;
import rental.dto.RentalResponseDTO;
import rental.entity.Rental;
import rental.respository.RentalRepository;
import user.entity.Users;
import user.repository.UserRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository,
                         BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public RentalResponseDTO createRental(RentalRequestDTO rentalRequestDTO) {
        if (rentalRequestDTO == null || rentalRequestDTO.book().getId() == null || rentalRequestDTO.user().getId() == null) {
            throw new IllegalArgumentException("bookId, userId는 필수입니다.");
        }

        // 이미 대출 중(미반납)인지 확인
        if (rentalRepository.existsByBookIdAndReturnDateIsNull(rentalRequestDTO.book().getId())) {
            throw new IllegalStateException("이미 대출 중인 도서입니다.");
        }

        Books book = bookRepository.findById(rentalRequestDTO.book().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서입니다."));
        Users user = userRepository.findById(rentalRequestDTO.user().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setUser(user);
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDate(null);
        Rental savedRental = rentalRepository.save(rental);

        return RentalResponseDTO.fromEntity(savedRental);
    }

    @Transactional
    public RentalResponseDTO createReturn(Long rentalId) {
        if (rentalId == null) throw new IllegalArgumentException("rentalId는 필수입니다.");

        Rental rental = rentalRepository.findByIdAndReturnDateIsNull(rentalId)
                .orElseThrow(() -> new IllegalStateException("이미 반납되었거나 존재하지 않습니다."));

        rental.setReturnDate(LocalDateTime.now());
        return RentalResponseDTO.fromEntity(rental);
    }

    public boolean existsRentalById(Long id) {
        return rentalRepository.existsById(id);
    }

    public boolean isBookCurrentlyRented(Long bookId) {
        return rentalRepository.existsByBookIdAndReturnDateIsNull(bookId);
    }
}
