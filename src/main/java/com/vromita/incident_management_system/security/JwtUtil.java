package com.vromita.incident_management_system.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;


    /**
     * Since JJWT to sign the token needs a SecretKey object, this method decode the secretKey in BASE64
     * then it converts in a SecretKey type
     * @return SecretKey type key
     */
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /**
     * Method used to generate the token
     * @param userDetails the authenticated user details
     * @return the token as String
     */
    public String generateToken(UserDetails userDetails){

        return Jwts.builder()
                .subject(userDetails.getUsername()) // Who is signing
                .issuedAt(new Date()) // Token issued date
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h expiration
                .signWith(getSigningKey()) // Sign the token
                .compact(); // closure which generate the String
    }

    /**
     * The method receive a token JWT as String and it extracts the username
     * @param token the JWT token string
     * @return the username
     */
    public String extractUsername(String token){

        return Jwts.parser()
                .verifyWith(getSigningKey()) // Verify the Key sign
                .build()
                .parseSignedClaims(token) // read the token
                .getPayload()
                .getSubject(); // extract the username
    }

    /**
     * The method verifies if the token is still valid. First compare the username of the token
     * with the username in the DB and then with extractUsername() if the token or the signature
     * is not valid it throws an exception
     * @param token the JWT token string
     * @param userDetails the authenticated user
     * @return true or false whether the token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails){

        return extractUsername(token).equals(userDetails.getUsername());
    }


}
