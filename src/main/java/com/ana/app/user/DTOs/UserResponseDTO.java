package com.ana.app.user.DTOs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {
    private String name;
    private String lastName;
    private String email;
}
