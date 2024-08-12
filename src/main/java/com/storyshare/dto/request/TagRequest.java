package com.storyshare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagRequest {
    @NotBlank
    @Size(min = 3, max = 10)
    @Pattern(regexp = "^#[a-zA-Z]+$", message = "Name must start with '#' followed by only letters")
    private String name;
}
