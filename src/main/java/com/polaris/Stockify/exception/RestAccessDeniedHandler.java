package com.polaris.Stockify.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polaris.Stockify.payload.ApiResponse;
import com.polaris.Stockify.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.HttpHeadersReturnValueHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        ErrorResponse response = new ErrorResponse();

        response.setError("Access Denied");
        response.setMessage("Not authorized to access resource");
        response.setStatus(HttpStatus.FORBIDDEN);

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}