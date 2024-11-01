package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.ApiRespone;
import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.entity.User;
import com.example.foodwed.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // dang ki
//    @PostMapping()
//    ApiRespone<User> createUser(@RequestBody @Valid UserCreateRequest request){
//        ApiRespone apiRespone = new ApiRespone<>();
//        apiRespone.setResult(userService.createUser(request));
//        return apiRespone;
//    }
}
