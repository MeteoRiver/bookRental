package rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.dto.RentalRequestDTO;
import rental.service.RentalService;

@RestController
@RequestMapping("/api/rental")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping()
    public ResponseEntity<?> createRental(RentalRequestDTO rentalRequestDTO) {
        return ResponseEntity.ok(rentalService.createRental(rentalRequestDTO));
    }
    @PostMapping
    public ResponseEntity<?> returnRental(Long id) {
        return ResponseEntity.ok(rentalService.createReturn(id));
    }
    @GetMapping()//미반납여부
    public ResponseEntity<?> getRentals(Long bookId) {
        return ResponseEntity.ok(rentalService.isBookCurrentlyRented(bookId));
    }

    @GetMapping//대츨 자체 존재여부
    public ResponseEntity<?> existRental(Long id) {
        return ResponseEntity.ok(rentalService.existsRentalById(id));
    }


}