package com.vromita.incident_management_system.security;

import com.vromita.incident_management_system.service.AppUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AppUserService appUserService;
    private final JwtUtil jwtUtil;


    public JwtFilter(AppUserService appUserService, JwtUtil jwtUtil) {
        this.appUserService = appUserService;
        this.jwtUtil = jwtUtil;
    }


    /**
     * doFilterInternal runs on every HTTP request. It reads the JWT token from the Authorization header, verifies it
     * via JwtUtil, loads the user from the DB via AppUserService, and if everything is valid it sets the authentication
     * in the SecurityContext. If the token is missing or invalid, the request passes through unauthenticated.
     * SecurityConfig will decide whether to block it or not.
     * @param request the incoming HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to pass the request along
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){

            filterChain.doFilter(request, response);
            return;

        }

        String authToken = authHeader.substring(7); // Extract the token without "Bearer "
        String authUsername = jwtUtil.extractUsername(authToken); // Extract the username from the token

        // Check if the username is not null and if the username does not have an authentication
        if (authUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user
            UserDetails loadedUser = appUserService.loadUserByUsername(authUsername);

            if(jwtUtil.isTokenValid(authToken, loadedUser)){

                UsernamePasswordAuthenticationToken authTokenDetails = new UsernamePasswordAuthenticationToken(
                        loadedUser,
                        null,
                        loadedUser.getAuthorities()
                );

                authTokenDetails.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authTokenDetails);

            }

        }

        filterChain.doFilter(request, response);
        return;

    }
}
