package com.ana.app.user.DTOs;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
    private String lastName;
    private String email;
}
