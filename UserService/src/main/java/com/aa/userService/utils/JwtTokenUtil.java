package com.aa.userservice.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

//    @Value("${jwt.secret}")
    private static String secret = "c2b2cc89ee37d8fd00fd5fa422e11da4ef8242153cc7a76ab711dc1a4a90a672054d11927e23fdde4cccdce6be84f08457191230db1789ff158305d88db9b729\n";

//    @Value("${jwt.access-token-expiration}")
    private static Long accessTokenExpiration =10L;

//    @Value("${jwt.refresh-token-expiration}")
    private static Long refreshTokenExpiration= 10L;

    // Generate Access Token
    public static final String generateAccessToken(String email, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000)) // Convert to ms
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Generate Refresh Token
    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000)) // Convert to ms
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Validate Token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid token");
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid signature");
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed");
        }
    }

    // Extract Email from Token
    public static String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract Claims from Token
    public static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get Signing Key
    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
