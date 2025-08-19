package jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.logout.LogoutFilter;

public class CustomLogoutFilter extends LogoutFilter {
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;

    public CustomLogoutFilter(JwtService jwtService, JwtUtil jwtUtil) {
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/login");
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !("/logout".equals(request.getRequestURI())&&);
    }
}
