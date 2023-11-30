package com.company.explorecalifornia.security;

import com.company.explorecalifornia.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The class {@code AuthenticationEntryPointImpl} is an implementation of the interface {@link AuthenticationEntryPoint}
 * which is used to send an HTTP response that requests credentials from a client.
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        LOGGER.info("Unauthenticated request: {}", authException.getMessage());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(),
                                                        "You are not authenticated",
                                                        LocalDateTime.now());
        response.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(errorResponse)));
    }
}