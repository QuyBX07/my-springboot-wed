package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.ApiRespone;
import com.example.foodwed.dto.Request.AuthRequest;
import com.example.foodwed.dto.response.AuthResponse;
import com.example.foodwed.service.AuthService;
import com.example.foodwed.service.UserService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthController {
//    AuthService authService;
//    @PostMapping("/login")
//    ApiRespone<AuthResponse> auththenticate(@RequestBody AuthRequest request){
//        boolean result =  authService.authenticate(request);
//        return  ApiRespone.<AuthResponse>builder()
//                .result(AuthResponse.builder()
//                        .authenticated(result)
//                        .build())
//                .build();
//    }
}
