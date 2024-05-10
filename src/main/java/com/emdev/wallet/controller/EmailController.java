package com.emdev.wallet.controller;

import java.util.Optional;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emdev.wallet.dto.ChangePasswordDto;
import com.emdev.wallet.dto.EmailValuesDto;
import com.emdev.wallet.exceptions.RequestException;
import com.emdev.wallet.service.EmailService;
import com.emdev.wallet.user.User;
import com.emdev.wallet.user.UserService;
import com.emdev.wallet.utils.Mensaje;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name="1. Auth endpoints")
public class EmailController {
    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String emailFrom;

    private static final String subject = "Change Password";

    @Operation(summary = "password forgot", description = "Send email to reset password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "404", description = "The user with that email does not exist")
    })
    @PostMapping("/password-forgot")
    public ResponseEntity<?> sendEmailTemplate( @RequestBody EmailValuesDto dto) {
        Optional<User> usuarioOpt = userService.getUserByEmail(dto.getMailTo());
        if (!usuarioOpt.isPresent())
            throw new RequestException("The user with that email does not exist", "P-404",
                    HttpStatus.NOT_FOUND);

        User usuario = usuarioOpt.get();

        dto.setMailFrom(emailFrom);
        dto.setSubject(subject);
        dto.setMailTo(usuario.getEmail());
        dto.setUserName(usuario.getUsername());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);
        usuario.setTokenPassword(tokenPassword);
        userService.save(usuario);
        emailService.sendEmail(dto);
        return new ResponseEntity<>(new Mensaje("We have sent you an email"), HttpStatus.OK);
    }

    @Operation(summary = "change password", description = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid fields"),
            @ApiResponse(responseCode = "406", description = "Passwords do not match"),
            @ApiResponse(responseCode = "404", description = "The user does not exist")
    })
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            throw new RequestException("Invalid fields", "P-400",
                    HttpStatus.BAD_REQUEST);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {

            throw new RequestException("Passwords do not match", "P-406",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<User> usuarioOpt = userService.getUserByTokenPassword(dto.getTokenPassword());
        if (!usuarioOpt.isPresent())

            throw new RequestException("The user does not exist", "P-404",
                    HttpStatus.NOT_FOUND);
        User usuario = usuarioOpt.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        usuario.setPassword(newPassword);
        usuario.setTokenPassword(null);
        userService.save(usuario);
        return new ResponseEntity<>(new Mensaje("Password updated successfully"), HttpStatus.OK);
    }
}
