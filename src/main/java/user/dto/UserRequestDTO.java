package user.dto;

import user.Role;

public record UserRequestDTO(
        Long id,
        String name,
        String email,
        String password,
        String phone,
        Role role) {
}
