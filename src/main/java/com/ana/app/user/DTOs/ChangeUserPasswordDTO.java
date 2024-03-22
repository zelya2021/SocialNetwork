package com.ana.app.user.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangeUserPasswordDTO {
    @NotBlank(message = "Old password cannot be blank")
    @Size(min = 8, message = "Old password should have at least 8 characters")
    public String oldPassword;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 8, message = "New password should have at least 8 characters")
    public String newPassword;
}
