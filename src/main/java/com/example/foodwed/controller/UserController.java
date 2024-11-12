package com.example.foodwed.controller;

<<<<<<< HEAD
import com.example.foodwed.dto.Request.ApiRespone;
import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.response.UserResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
=======
import com.example.foodwed.service.UserService;
>>>>>>> cf1313390f71f96720bf92ff14226d945e5e1842
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping()
    ApiRespone<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return  ApiRespone.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }
}
