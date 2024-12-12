package com.example.foodwed.service;

import com.example.foodwed.entity.Email;
import com.example.foodwed.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class GmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    /**
     * Gửi email đến tất cả email trong cơ sở dữ liệu.
     *
     * @param subject Chủ đề của email.
     * @param message Nội dung của email.
     */
    public void sendBulkEmails(String subject, String message) {
        List<Email> emails = emailRepository.findAll();
        if (emails.isEmpty()) {
            throw new IllegalArgumentException("No email addresses found in the database.");
        }

        emails.forEach(email -> {
            sendEmail(email.getEmailAddress(), subject, message);
        });
    }

    /**
     * Gửi một email đơn lẻ.
     *
     * @param toEmail Địa chỉ email người nhận.
     * @param subject Chủ đề của email.
     * @param message Nội dung của email.
     */
    private void sendEmail(String toEmail, String subject, String message) {
        try {
            // Cấu hình JavaMailSender
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587); // Cổng gửi email
            mailSender.setUsername("hieu0190066@huce.edu.vn"); // Thay bằng địa chỉ email của bạn
            mailSender.setPassword("udhs zqvc qhcs ngkp"); // Thay bằng mật khẩu của bạn

            // Cấu hình các thuộc tính SMTP
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.host", "smtp.office365.com");
            props.setProperty("mail.smtp.starttls.enable", "true"); // Bật STARTTLS
            props.put("mail.smtp.post", "587");
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2"); // Cấu hình sử dụng TLSv1.2
            props.setProperty("mail.smtp.auth", "true"); // Bật xác thực
            props.put("mail.smtp.ssl.trust", "*");

            // Tạo email và gửi
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom("hieu0190066@huce.edu.vn"); // Thay bằng địa chỉ email của bạn

            mailSender.send(mailMessage);
            System.out.println("Email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Error sending email to: " + toEmail + " - " + e.getMessage());
            throw e; // Rethrow để xử lý ở controller
        }
    }

    public void sendResetPasswordEmail(String toEmail, String newPassword) {
        String subject = "Khôi phục mật khẩu Daily Cook";
        String message = "Xin chào,\n\n"
                + "Mật khẩu mới của bạn là: " + newPassword + "\n\n"
                + "Vui lòng đăng nhập và đổi mật khẩu ngay lập tức.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ Daily Cook";

        sendEmail(toEmail, subject, message);
    }

}
