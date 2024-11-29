package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.GoogleRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.GoogleResponse;
import com.example.foodwed.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class GoogleController {

    @Autowired
    private GoogleService googleService;

    @PostMapping("/google")
    public ResponseEntity<ApiRespone<GoogleResponse>> googleLogin(@RequestBody GoogleRequest googleRequest) {
        try {
            // Xác minh token Google thông qua GoogleService
            GoogleResponse googleResponse = googleService.verifyGoogleToken(googleRequest.getToken());

            // Tạo ApiRespone trả về với token trong result
            ApiRespone<GoogleResponse> apiResponse = ApiRespone.<GoogleResponse>builder()
                    .status("success")
                    .message("Đăng nhập Google thành công")
                    .result(googleResponse) // result chứa thông tin người dùng và token
                    .build();

            return ResponseEntity.ok(apiResponse); // Trả về kết quả với thông tin người dùng
        } catch (IllegalArgumentException e) {
            ApiRespone<GoogleResponse> apiResponse = ApiRespone.<GoogleResponse>builder()
                    .status("failure")
                    .message("Token không hợp lệ: " + e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(401).body(apiResponse); // Trả về lỗi nếu token không hợp lệ
        } catch (Exception e) {
            ApiRespone<GoogleResponse> apiResponse = ApiRespone.<GoogleResponse>builder()
                    .status("failure")
                    .message("Lỗi server: " + e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(500).body(apiResponse); // Trả về lỗi server nếu có
        }
    }
}
