package user.dto;

import user.Role;
import user.entity.Users;

public class UserResponseDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;

    public static UserResponseDTO fromEntity(Users user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.name = user.getName();
        userResponseDTO.email = user.getEmail();
        userResponseDTO.password = user.getPassword();
        userResponseDTO.phone = user.getPhone();
        userResponseDTO.role = user.getRole();
        return userResponseDTO;
    }
}
