package com.metoeriver.library.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    public JwtFilter(JwtUtil jwtUtil, JwtService jwtService) {
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String token = getToken(req);
        if (token == null) {
            chain.doFilter(req, res);
        }
        try{
            Jws<Claims> jws = jwtUtil.parseAccess(token);
            Claims claims = jws.getPayload();

            if(!"access".equals(claims.get("category", String.class))) {
                send401(res, "invalid token category");
            }
            if(jwtUtil.isExpired(claims)) {
                send401(res, "expired token");
            }

            String jti = claims.getId();
            if(jwtService.isAccessBlacklisted(jti)){
                send401(res, "access blacklisted");
            }

            String userId = claims.getSubject();
            String role = claims.get("role", String.class);
            var auth = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(req, res);

        }catch(Exception e){
            send401(res, "invalid jwt");
        }

    }




    private String getToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        if(req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("access")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void send401(HttpServletResponse res, String msg) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write(" [error] : " + msg);
    }
}
