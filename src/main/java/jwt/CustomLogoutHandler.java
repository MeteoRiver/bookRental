package jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final boolean userRefresh;
    private final boolean blacklistAccess;

    public CustomLogoutHandler(JwtService jwtService, JwtUtil jwtUtil,  boolean userRefresh, boolean blacklistAccess) {
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.userRefresh = userRefresh;
        this.blacklistAccess = blacklistAccess;
    }

    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {

        String accessToken = getToken(req);
        if (accessToken == null) {
            accessToken = getTokenFromCookie(req, "access");
        }

        if(blacklistAccess&&accessToken!=null) {
            try{
                String jti = jwtUtil.extractJti(accessToken);
                long remainMs = jwtUtil.remainingMs(accessToken);
                if(jti !=null && remainMs>0) {
                    jwtService.blacklistAccess(jti,remainMs);
                }
            }catch(Exception ignored){}
        }

        if(userRefresh) {
            String refreshToken = getTokenFromCookie(req, "refresh");
            if(refreshToken!=null) {
                try {
                    String jti = jwtUtil.extractJti(refreshToken);
                    if(jti!=null) {
                        jwtService.deleteRefresh(jti);
                    }
                }catch(Exception ignored){}
            }
        }

        expireCookie(res,"access");
        expireCookie(res,"refresh");
    }

    private String getToken(HttpServletRequest req){
        String token = req.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    private String getTokenFromCookie(HttpServletRequest req, String name){
        if(req.getCookies()==null){
            return null;
        }
        for(Cookie cookie : req.getCookies()){
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
    }

    private void expireCookie(HttpServletResponse res, String name){
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

}
