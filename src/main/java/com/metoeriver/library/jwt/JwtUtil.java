package com.metoeriver.library.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.metoeriver.library.user.Role;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final SecretKey refreshSecret;

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey, @Value("${spring.jwt.refreshSecret}") String refreshSecret) {
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "HmacSHA256");
        this.refreshSecret = new SecretKeySpec(Base64.getDecoder().decode(refreshSecret), "HmacSHA256");
    }

    public String createJwt(Long id, String name, Role role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + 86400); //만료시간 1일 -> second
        return Jwts.builder()
                .claim("category", "access")
                .subject(String.valueOf(id))
                .claim("name",name)
                .claim("role",role.name())
                .issuer("MeteoRiver")
                .issuedAt(now)
                .expiration(exp)
                .signWith(secretKey)
                .compact();
    }

    public String createJwtRefresh(Long id, String name, Role role) {
        Date now = new Date();
        long ttlMs = 604_800_000L; // 7 days in milliseconds
        Date exp = new Date(now.getTime() + ttlMs);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claim("category", "refresh")
                .subject(String.valueOf(id))
                .issuer("MeteoRiver")
                .issuedAt(now)
                .expiration(exp)
                .signWith(refreshSecret)
                .compact();
    }
    public Boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
    public String getCategory(Claims claims) {
        Object c = claims.get("category");
        return c == null ? null : c.toString();
    }

    public Jws<Claims> parseAccess(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)  // 키 등록
                .build()
                .parseSignedClaims(token); // 서명 검증 + 클레임 반환
    }
    public Jws<Claims> parseAccessRefresh(String token) {
        return Jwts.parser()
                .verifyWith(refreshSecret)  // 키 등록
                .build()
                .parseSignedClaims(token); // 서명 검증 + 클레임 반환
    }
    public String getId(Claims claims) { return claims.getId(); }
    public String getName(Claims claims) { return claims.get("name").toString(); }
    public String getRole(Claims claims)     { return claims.get("role").toString(); }
    public String extractJti(String Token) {
        return parseAccess(Token).getPayload().getId();
    }
    public long remainingMs(String Token) {
        Date exp = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(Token).getPayload().getExpiration();
        return  Math.max(0, exp.getTime() - System.currentTimeMillis());
    }
    public String extractRefreshJti(String refreshToken) {
        return Jwts.parser()
                .verifyWith(refreshSecret)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getId(); // jti
    }

    public long remainingRefreshMillis(String refreshToken) {
        Date exp = Jwts.parser()
                .verifyWith(refreshSecret)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getExpiration();
        long remain = exp.getTime() - System.currentTimeMillis();
        return Math.max(0L, remain);
    }

}