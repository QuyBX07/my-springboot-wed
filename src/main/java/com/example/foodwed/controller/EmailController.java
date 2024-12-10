package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.EmailRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.EmailResponse;
import com.example.foodwed.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Add a new email
    @PostMapping
    public ResponseEntity<ApiRespone<EmailResponse>> addEmail(@RequestBody EmailRequest request) {
        EmailResponse response = emailService.addEmail(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiRespone<>("success", "201", "Email added successfully", response));
    }

    // Get all emails
    @GetMapping
    public ResponseEntity<ApiRespone<List<EmailResponse>>> getAllEmails() {
        List<EmailResponse> responses = emailService.getAllEmails();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<>("success", "200", "Emails retrieved successfully", responses));
    }
}

