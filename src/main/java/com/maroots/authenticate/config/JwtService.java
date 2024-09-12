package com.maroots.authenticate.config;

import com.maroots.authenticate.user.User;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${security.jwt.expiration-minute}")
    public long EXPIRATION_MINUTE;

    @Value("${security.jwt.secret-key}")
    public String SECRET_KEY;
    public String generateToken(User user, Map<String, Object> claims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTE *60 * 1000));
                return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                        .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY)), SignatureAlgorithm.HS256)
                        .compact();
    }

    private Key generateKey() {
        byte[] secretBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
    }
}
