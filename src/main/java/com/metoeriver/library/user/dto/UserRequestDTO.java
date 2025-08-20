package com.metoeriver.library.user.dto;

import com.metoeriver.library.user.Role;

public record UserRequestDTO(
        Long id,
        String name,
        String email,
        String password,
        String phone,
        Role role) {
}
