package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.AuthRequest;
import com.example.foodwed.dto.Request.IntrospectRequest;
import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.AuthResponse;
import com.example.foodwed.dto.response.IntrospectResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.repository.UserReponsitory;
import com.example.foodwed.service.AuthService;
import com.example.foodwed.service.GmailService;
import com.example.foodwed.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    private GmailService gmailService;

    @Autowired
    private UserReponsitory userReponsitory;

    @Autowired
    private PasswordEncoder passwordEncoder; // Thêm PasswordEncoder

    @PostMapping("/signup")
    ApiRespone<User> userSignup(@RequestBody UserCreateRequest request) {
        ApiRespone apiRespone = new ApiRespone<>();
        apiRespone.setResult(userService.createUser(request));
        return apiRespone;
    }

    @PostMapping("/token")
    ApiRespone<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        var result = authService.authenticate(request);
        return ApiRespone.<AuthResponse>builder()
                .status("success")
                .message("Đăng nhập thành công")
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiRespone<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return ApiRespone.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Kiểm tra email trong cơ sở dữ liệu
        Optional<User> optionalUser = userReponsitory.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email không tồn tại trong hệ thống.");
        }

        User user = optionalUser.get();

        // Tạo mật khẩu mới
        String newPassword = generateRandomPassword();

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(newPassword));
        userReponsitory.save(user);

        // Gửi email khôi phục mật khẩu
        try {
            gmailService.sendResetPasswordEmail(email, newPassword);
            return ResponseEntity.ok("Mật khẩu mới đã được gửi đến email của bạn.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể gửi email. Vui lòng thử lại sau.");
        }
    }

    private String generateRandomPassword() {
        // Tạo mật khẩu ngẫu nhiên dài 8 ký tự
        return UUID.randomUUID().toString().substring(0, 8);
    }


    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(7); // Loại bỏ chữ "Bearer "

            // Gọi phương thức refreshToken trong AuthService
            AuthResponse authResponse = authService.refreshToken(token);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Token refreshed successfully");
            response.put("result", authResponse);

            return ResponseEntity.ok(response);
        } catch (Appexception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            log.error("Error refreshing token", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Internal server error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




}
