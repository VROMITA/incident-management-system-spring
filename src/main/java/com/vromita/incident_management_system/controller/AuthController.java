package com.vromita.incident_management_system.controller;

import com.vromita.incident_management_system.dto.AuthRequest;
import com.vromita.incident_management_system.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing authentication
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * The method firstly authenticate the user then extract the credentials and create the token
     * @param request DTO containing username and password
     * @return 200 OK with JWT token as String
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Extract the authentified user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));

    }

    /**
     * The method return 200 status code with a message.
     * Being stateless, the server doesn't keep in memory the token.
     * The client should delete the token but the on the server side the token will
     * remain active until the expiration
     *
     * @return 200 code plus logout message
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.status(200).body("You have been logged out");
    }
}
