// GmailController.java
package com.example.foodwed.controller;

import com.example.foodwed.service.EmailService;
import com.example.foodwed.service.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gmail")
public class GmailController {

    @Autowired
    private GmailService gmailService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendBulkEmails(@RequestParam String subject, @RequestParam String messageBody) {
        List<String> emailAddresses = emailService.getAllEmails()
                .stream()
                .map(emailResponse -> emailResponse.getEmailAddress())
                .toList();
        gmailService.sendEmails(emailAddresses, subject, messageBody);
        return ResponseEntity.status(HttpStatus.OK).body("Emails sent successfully!");
    }
}
