package com.example.chatmessages.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFullRequest {
    @NotBlank(message = "Username is required")
    String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    String email;

    String avatar;

    String status;

    Instant createdAt;
}
