package user.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dto.UserRequestDTO;
import user.dto.UserResponseDTO;
import user.entity.Users;
import user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Users user = new Users();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        user.setPhone(userRequestDTO.phone());
        user.setRole(userRequestDTO.role());

        Users savedUser = userRepository.save(user);

        return  UserResponseDTO.fromEntity(savedUser);
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        Users user = userRepository.findById(userRequestDTO.id()).orElseThrow(()->new RuntimeException("ERROR수정"));

        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        user.setPhone(userRequestDTO.phone());
        user.setRole(userRequestDTO.role());
        Users savedUser = userRepository.save(user);
        return  UserResponseDTO.fromEntity(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::fromEntity).toList();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("에러");
        }
        userRepository.deleteById(id);
        return (!userRepository.existsById(id));
    }
}
