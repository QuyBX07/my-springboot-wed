package com.example.foodwed.dto.response;

import com.example.foodwed.entity.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailResponse {
    private String id;
    private String emailAddress;
    private LocalDateTime createdAt;

    public EmailResponse(Email email) {
        this.id = email.getId();
        this.emailAddress = email.getEmailAddress();
        this.createdAt = email.getCreatedAt();
    }
}
