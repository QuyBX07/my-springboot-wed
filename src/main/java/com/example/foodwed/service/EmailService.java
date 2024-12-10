package com.example.foodwed.service;

import com.example.foodwed.dto.Request.EmailRequest;
import com.example.foodwed.dto.response.EmailResponse;
import com.example.foodwed.entity.Email;
import com.example.foodwed.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;



    // Add new email
    public EmailResponse addEmail(EmailRequest request) {
        if (emailRepository.existsByEmailAddress(request.getEmailAddress())) {
            throw new IllegalArgumentException("Email already exists!");
        }

        Email email = new Email();
        email.setEmailAddress(request.getEmailAddress());
        Email savedEmail = emailRepository.save(email);
        return new EmailResponse(savedEmail);
    }

    // Get all emails
    public List<EmailResponse> getAllEmails() {
        return emailRepository.findAll().stream()
                .map(EmailResponse::new)
                .collect(Collectors.toList());
    }
}

