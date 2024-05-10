package com.emdev.wallet.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "1. Auth endpoints", description = "Register/Login/Reset password endpoints")
public class AuthenticationController {

	private final AuthenticationService service;

	@Operation(summary = "Register a new user", description = "Register a new user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully register"),
			@ApiResponse(responseCode = "409", description = "There is already a user with that email")
	})
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
	}

	@Operation(summary = "Login a user", description = "Login a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully login with the given credentials"),
			@ApiResponse(responseCode = "400", description = "Bad credentials"),
			@ApiResponse(responseCode = "500", description = "Email not registered")
	})
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

}
