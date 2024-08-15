package com.storyshare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @Size(min = 8, max = 64, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?!.,]).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"
    )
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
