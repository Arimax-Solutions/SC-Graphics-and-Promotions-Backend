package com.sc_graghics.sc_graphic.controller;


import com.sc_graghics.sc_graphic.entity.User;
import com.sc_graghics.sc_graphic.repo.UserRepository;
import com.sc_graghics.sc_graphic.service.custom.UserService;
import com.sc_graghics.sc_graphic.service.security.AuthenticationService;
import com.sc_graghics.sc_graphic.util.payload.request.LoginRequest;
import com.sc_graghics.sc_graphic.util.payload.request.RegisterRequest;
import com.sc_graghics.sc_graphic.util.payload.respond.AuthenticationResponse;
import com.sc_graghics.sc_graphic.util.payload.respond.StandardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author : Chanuka Weerakkody
 * @since : 20.1.1
 **/
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> register(@RequestBody RegisterRequest request) {
        log.info("register {} " + request);

        if (!userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.ok(
                    StandardResponse.builder()
                            .message("User registered successfully")
                            .status(200)
                            .data(AuthenticationResponse.builder()
                                    .token(authenticationService
                                            .register(request))
                                    .build())
                            .build());
        }
        return ResponseEntity.ok(StandardResponse.builder()
                .message("User already exists.")
                .status(400)
                .build());
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        log.info("Login request: {}", request);
        AuthenticationResponse response = authenticationService.authenticate(request);
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            User authenticatedUser = userOptional.get();
            response.setAuthenticatedUser(authenticatedUser);
        } else {
            throw new RuntimeException("User not found for username: " + request.getUsername());
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "User Deleted", null), HttpStatus.OK);
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getAllUsers() {
        return new ResponseEntity<>(
                new StandardResponse(200, "Users Fetched", userService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new StandardResponse(200, "User Fetched", userService.findById(id)), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse> updateUser(@PathVariable("id") Integer id, @RequestBody User updatedUserData) {
        try {
            updatedUserData.setUser_id(id);
            userService.update(updatedUserData);

            return ResponseEntity.ok(new StandardResponse(200, "User updated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(500, "Failed to update user", null));
        }
    }
}
