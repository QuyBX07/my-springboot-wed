//package com.example.foodwed.service;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.model.Message;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Base64;
//import java.util.Properties;
//
//@Service
//public class GmailService {
//
//    private static final String APPLICATION_NAME = "FoodWed";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//
//    private final String accessToken;
//
//    // Constructor để khởi tạo GmailService
//    public GmailService(String accessToken) {
//        if (accessToken == null || accessToken.isEmpty()) {
//            throw new IllegalArgumentException("Access token không được để trống.");
//        }
//        this.accessToken = accessToken;
//    }
//
//    /**
//     * Tạo đối tượng Gmail service.
//     */
//    private Gmail createGmailService() throws GeneralSecurityException, IOException {
//        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
//
//        return new Gmail.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JSON_FACTORY,
//                credential
//        ).setApplicationName(APPLICATION_NAME).build();
//    }
//
//    /**
//     * Gửi email.
//     */
//    public void sendEmail(String recipient, String subject, String bodyText)
//            throws MessagingException, IOException, GeneralSecurityException {
//        Gmail gmailService = createGmailService();
//        MimeMessage mimeMessage = createMimeMessage(recipient, "me", subject, bodyText);
//        Message message = createMessage(mimeMessage);
//        gmailService.users().messages().send("me", message).execute();
//    }
//
//    /**
//     * Tạo MimeMessage từ thông tin email.
//     */
//    private MimeMessage createMimeMessage(String to, String from, String subject, String bodyText)
//            throws MessagingException {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//        email.setFrom(new InternetAddress(from));
//        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
//        email.setSubject(subject);
//        email.setText(bodyText);
//
//        return email;
//    }
//
//    /**
//     * Chuyển đổi MimeMessage thành Message của Gmail API.
//     */
//    private Message createMessage(MimeMessage mimeMessage) throws MessagingException, IOException {
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        mimeMessage.writeTo(buffer);
//        byte[] rawEmailBytes = buffer.toByteArray();
//        String encodedEmail = Base64.getUrlEncoder().encodeToString(rawEmailBytes);
//
//        Message message = new Message();
//        message.setRaw(encodedEmail);
//        return message;
//    }
//}
