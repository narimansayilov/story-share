package com.storyshare.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Integer storyCount;
    private String photoUrl;
}
