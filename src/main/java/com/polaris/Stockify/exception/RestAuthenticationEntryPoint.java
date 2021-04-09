package com.polaris.Stockify.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polaris.Stockify.payload.ApiResponse;
import com.polaris.Stockify.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        ErrorResponse response = new ErrorResponse();

        response.setError("Bad Credentials");
        response.setMessage("Invalid email/password");
        response.setStatus(HttpStatus.UNAUTHORIZED);

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}
