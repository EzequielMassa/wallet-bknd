package com.emdev.wallet.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emdev.wallet.auth.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name="7. Users endpoints",description = "Get/Update user endpoints")
public class UserController {

    private final UserService service;

    // get all users
    @Operation(summary = "Get all users", description = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve all users"),
    })
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    // update user profile
    @Operation(summary = "Update user profile", description = "Update user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully update user profile by user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PutMapping("/update")
    public ResponseEntity<AuthenticationResponse> updateUser(@Valid  @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.updateUser(request));
    }
}
