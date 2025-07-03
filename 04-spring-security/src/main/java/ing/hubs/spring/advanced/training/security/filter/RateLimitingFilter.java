package ing.hubs.spring.advanced.training.security.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A sample of a security {@link jakarta.servlet.Filter}
 *
 * @author bogdan.solga
 */
public class RateLimitingFilter implements Filter {
    private final ConcurrentHashMap<String, AtomicLong> requestCount = new ConcurrentHashMap<>();
    private final static long RATE_LIMIT = 100;

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipAddress = request.getRemoteAddr();
        AtomicLong count = requestCount.computeIfAbsent(ipAddress, k -> new AtomicLong());

        if (count.incrementAndGet() > RATE_LIMIT) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Rate limit exceeded. Please try again later.");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
