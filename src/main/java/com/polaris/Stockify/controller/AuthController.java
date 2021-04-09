package com.polaris.Stockify.controller;

import com.polaris.Stockify.dto.SignUpDto;
import com.polaris.Stockify.payload.ApiResponse;
import com.polaris.Stockify.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ApiResponse createUserAccount(@Valid @RequestBody SignUpDto signUpDto) {

        return authService.registerUser(signUpDto);
    }

}