package com.metoeriver.library.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.metoeriver.library.user.Role;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    public CustomLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,  JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
    String username;
    String password;
    try{
        if(isJson(req)){
            try(ServletInputStream in = req.getInputStream()){
                Map<String, Object> body = new ObjectMapper().readValue(in, Map.class);
                username = (String)body.get("email");
                password = (String)body.get("password");
            }
        }else {
            username = (String)req.getParameter("email");
            password = (String)req.getParameter("password");
        }
    }
    catch(IOException e){
        throw new BadCredentialsException(e.getMessage());
    }
    if((username==null||username.trim().equals(""))|| (password==null||password.trim().equals(""))){
        throw new BadCredentialsException("아이디와 비밀번호가 비어있습니다.");
    }
    var authRequest = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException{
        Object principal = auth.getPrincipal();
        Long id = extractLong(principal, "getId");
        String name = extractString(principal, "getName");
        String roleS = extractString(principal, "getRole");
        Role role = Role.valueOf(roleS);

        String accessToken = jwtUtil.createJwt(id, name, role);
        String refreshToken = jwtUtil.createJwtRefresh(id, name, role);
        String refreshJti = jwtUtil.extractJti(refreshToken);
        long ttlMs = jwtUtil.remainingRefreshMillis(refreshToken);

        jwtService.saveRefresh(refreshJti, String.valueOf(id), ttlMs);

        res.setHeader("Authorization", "Bearer " + accessToken);
        setCookie(res, accessToken);
        setCookieRefresh(res, accessToken, ttlMs);
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setContentType("application/json");
        res.getWriter().write("{\"message\":\"login success\"}");
    }

    protected void unsuccessfulAuthentication(HttpServletRequest req,
                                              HttpServletResponse res,
                                              AuthenticationException failed) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setContentType("application/json");
        res.getWriter().write("{\"error\":\"login failed\"}");
    }

    private boolean isJson(HttpServletRequest req){
        String contentType = req.getContentType();
        if(contentType.contains("application/json")){
            return true;
        }
        return false;
    }
    private Long extractLong(Object value, String method){
        try{return (Long) value.getClass().getMethod(method).invoke(value);}catch(Exception e){return null;}
    }
    private String extractString(Object value, String method){
        try{return String.valueOf(value.getClass().getMethod(method).invoke(value));}catch(Exception e){return null;}
    }
    private void setCookie(HttpServletResponse res, String accessToken){
        Cookie cookie = new Cookie("access", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        //ac.setSecure(true);
        res.addCookie(cookie);
    }
    private void setCookieRefresh(HttpServletResponse res, String accessToken, long ttlMs){
        Cookie cookie = new Cookie("refresh", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (ttlMs / 1000));
        //ac.setSecure(true);
        res.addCookie(cookie);
    }

}
