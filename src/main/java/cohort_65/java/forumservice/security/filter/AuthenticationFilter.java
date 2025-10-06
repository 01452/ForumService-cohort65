package cohort_65.java.forumservice.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Component
public class AuthenticationFilter implements Filter {
    //TODO add repository for user accounts

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().equals("/account/register") || request.getRequestURI().equals("/forum/posts")) {
            filterChain.doFilter(request, response);
        }

        String value = request.getHeader("Authorization");
        String[] credentials = getCredentials(value);
        //TODO check pass and login in db

//        if (!credentials[0].equals("Andy")) {
//            throw new ServletException("Invalid credentials! You are not Andy!!!");
//        }
//        if (!credentials[1].equals("1234")) {
//            throw new ServletException("Invalid credentials! You are not Andy!!!");
//        }
        request = new WrappedRequest(request, credentials[0]);
        filterChain.doFilter(request, response);
    }

    //Basic SmF2YUZhbjoxMjM0
    private String[] getCredentials(String value) {
        String token = value.split(" ")[1];
        //SmF2YUZhbjoxMjM0
        String decoded = new String(Base64.getDecoder().decode(token));
        //JavaFan:1234
        return decoded.split(":");
    }

    private class WrappedRequest extends HttpServletRequestWrapper {
        private String login;

        public WrappedRequest(HttpServletRequest request, String login) {
            super(request);
            this.login = login;
        }

        @Override
        public Principal getUserPrincipal() {
            return () -> login;
        }
    }
}






















