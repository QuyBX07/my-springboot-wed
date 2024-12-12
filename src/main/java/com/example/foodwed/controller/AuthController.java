package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.AuthRequest;
import com.example.foodwed.dto.Request.IntrospectRequest;
import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.AuthResponse;
import com.example.foodwed.dto.response.IntrospectResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.repository.UserReponsitory;
import com.example.foodwed.service.AuthService;
import com.example.foodwed.service.GmailService;
import com.example.foodwed.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
}
