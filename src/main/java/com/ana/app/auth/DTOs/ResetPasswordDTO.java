package com.ana.app.auth.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String newPassword;
    private Integer resetPasswordCode;
}
