package com.example.tooktook.component.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;


    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String generate(String subject, Date expiredAt) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }


    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }


    public void validate(String accessToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (SignatureException ex) {
            throw new JwtException("invalid token request exception - Incorrect signature");
        } catch (MalformedJwtException ex) {
            throw new JwtException("invalid token request exception - malformed jwt token");
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (UnsupportedJwtException ex) {
            throw new JwtException("invalid token request exception - Illegal argument token");
        }
    }


    private Claims parseClaims(String accessToken) {

        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(accessToken)
            .getBody();

    }
}
