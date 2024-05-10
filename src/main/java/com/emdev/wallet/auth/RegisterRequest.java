package com.emdev.wallet.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First Name is required")
    @Size(min = 3, max = 15)
    private String firstname;
    @NotBlank(message = "Last Name is required")
    @Size(min = 3, max = 15)
    private String lastname;
    @Email(message = "Email format is not valid")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100)
    private String password;

    private String urlImg;
}
