package user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.dto.UserResponseDTO;
import user.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
}
