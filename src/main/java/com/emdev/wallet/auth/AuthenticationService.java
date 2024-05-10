package com.emdev.wallet.auth;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emdev.wallet.config.JwtService;
import com.emdev.wallet.exceptions.RequestException;
import com.emdev.wallet.model.Account;
import com.emdev.wallet.user.Role;
import com.emdev.wallet.user.User;
import com.emdev.wallet.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                Optional<User> usuarioOpt = userRepository.findByEmail(request.getEmail());
                if (usuarioOpt.isPresent()) {
                        throw new RequestException("There is already a user with that email", "P-409",
                                HttpStatus.CONFLICT);
                }else {
                        var accounts = new ArrayList<Account>();
                        accounts.add(new Account());
                        var newUser = User.builder()
                                .firstName(request.getFirstname())
                                .lastName(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .accounts(accounts)
                                .urlImg(request.getUrlImg())
                                .build();
                        userRepository.save(newUser);
                        var jwtToken = jwtService.generateToken(newUser);
                        return AuthenticationResponse.builder()
                                .id(newUser.getId())
                                .firstname(newUser.getFirstName())
                                .lastname(newUser.getLastName())
                                .email(newUser.getUsername())
                                .token(jwtToken)
                                .urlImg(newUser.getUrlImg())
                                .build();
                }
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                try {
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                        request.getEmail(),
                                        request.getPassword()));
                } catch (Exception e) {

                        throw new RequestException("Bad credentials", "P-400",
                                        HttpStatus.BAD_REQUEST);
                }
                var user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RequestException("Email not registered", "P-500",
                                                HttpStatus.INTERNAL_SERVER_ERROR));

                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .id(user.getId())
                                .firstname(user.getFirstName())
                                .lastname(user.getLastName())
                                .email(user.getUsername())
                                .token(jwtToken)
                                .urlImg(user.getUrlImg())
                                .build();
        }
}
