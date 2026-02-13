package md.utm2026.mvc.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class SimpleAuthFilter extends OncePerRequestFilter {

    private final String apiKey;
    private final String headerName;

    public SimpleAuthFilter(
            @Value("${app.api-key}") String apiKey,
            @Value("${app.api-key-header:X-API-KEY}") String headerName) {
        this.apiKey = apiKey;
        this.headerName = headerName;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/api/")) {
            return false;
        }
        return "/upload".equals(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String provided = request.getHeader(headerName);
        if (provided == null || !provided.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().write("Unauthorized");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
