package md.utm2026.mvc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long start = System.currentTimeMillis();
        request.setAttribute("requestStartMs", start);
        logger.info("IN  {} {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Object startAttr = request.getAttribute("requestStartMs");
        long durationMs = 0L;
        if (startAttr instanceof Long start) {
            durationMs = System.currentTimeMillis() - start;
        }
        logger.info("OUT {} {} -> {} ({} ms)",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                durationMs);
    }
}
