package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.ApiRespone;
import com.example.foodwed.dto.Request.AuthRequest;
import com.example.foodwed.dto.Request.IntrospectRequest;
import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.response.AuthResponse;
import com.example.foodwed.dto.response.IntrospectResponse;
import com.example.foodwed.dto.response.UserResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.service.AuthService;
import com.example.foodwed.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;
    //dang ki
    @PostMapping("/signup")
    ApiRespone<User> userSignup(@RequestBody UserCreateRequest request){
        ApiRespone apiRespone = new ApiRespone<>();
        apiRespone.setResult(userService.createUser(request));
        return apiRespone;
    }

    @PostMapping("/token")
    ApiRespone<AuthResponse> authenticate(@RequestBody AuthRequest request){
        var result = authService.authenticate(request);
        return ApiRespone.<AuthResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiRespone<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return ApiRespone.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
