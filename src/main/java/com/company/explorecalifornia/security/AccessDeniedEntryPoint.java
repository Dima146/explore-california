package com.company.explorecalifornia.security;

import com.company.explorecalifornia.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The class {@code AccessDeniedHandlerEntryPoint} is an implementation of the interface {@link AccessDeniedHandler}
 * which is used to handle access denied failure.
 *
 */
@Component
public class AccessDeniedEntryPoint implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedEntryPoint.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
            throws IOException, ServletException {
        LOGGER.error("Access to resource denied", exc);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.toString(),
                                                        "You do not have permission to access this resource",
                                                        LocalDateTime.now());
        response.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(errorResponse)));

    }
}