package com.example.foodwed.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
