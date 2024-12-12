package com.example.foodwed.controller;

import com.example.foodwed.service.GmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gmail")
public class GmailController {

    @Autowired
    private GmailService gmailService;

    /**
     * API để gửi email hàng loạt với body JSON.
     *
     * @param requestBody Payload JSON chứa subject và message.
     * @return ResponseEntity với trạng thái thành công hoặc lỗi.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendBulkEmails(@RequestBody EmailRequestBody requestBody) {
        try {
            // Gọi dịch vụ để gửi email hàng loạt
            gmailService.sendBulkEmails(requestBody.getSubject(), requestBody.getMessage());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Emails sent successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()); // Thông báo nếu không có email trong cơ sở dữ liệu
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while sending emails."); // Thông báo lỗi khi gửi email
        }
    }

    // Class đại diện cho payload JSON
    @Data
    static class EmailRequestBody {
        private String subject;
        private String message;
    }
}
