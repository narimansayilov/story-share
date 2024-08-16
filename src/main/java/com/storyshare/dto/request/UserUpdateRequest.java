package com.storyshare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @Size(min = 3, max = 50, message = "Username must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters, numbers, dots, underscores, and dashes")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 3, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zƏəÖöÜüŞşÇçığ]+$", message = "Name must contain only Azerbaijani letters")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Size(min = 3, max = 50, message = "Surname must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zƏəÖöÜüŞşÇçığ]+$", message = "Surname must contain only Azerbaijani letters")
    @NotBlank(message = "Surname cannot be blank")
    private String surname;
}
