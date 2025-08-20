package com.metoeriver.library.rental.respository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metoeriver.library.rental.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Boolean existsByBookId(Long id);
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
    Optional<Rental> findByIdAndReturnDateIsNull(Long id);

}
