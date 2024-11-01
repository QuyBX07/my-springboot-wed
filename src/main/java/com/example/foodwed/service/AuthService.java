package com.example.foodwed.service;

import com.example.foodwed.dto.Request.AuthRequest;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthService {
//    UserReponsitory userReponsitory;
//    public boolean authenticate(AuthRequest request){
//        var user = userReponsitory.findByEmail(request.getEmail()).orElseThrow(() -> new Appexception(ErrorCode.USER_EMAIL_Error));
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        return passwordEncoder.matches(request.getPassword(), user.getPassword());
//    }
}
