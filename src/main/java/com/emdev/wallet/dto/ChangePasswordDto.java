package com.emdev.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100)
    private String password;
    @NotBlank(message = "Confirmation is required")
    @Size(min = 4, max = 100)
    private String confirmPassword;
    @NotBlank
    private String tokenPassword;
}
