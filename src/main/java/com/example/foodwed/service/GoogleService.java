package com.example.foodwed.service;

import com.example.foodwed.dto.response.GoogleResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.enums.Role;
import com.example.foodwed.repository.UserReponsitory;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory; // Chỉ hoạt động khi đúng dependency
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.StringJoiner;
@Slf4j
@Service
public class GoogleService {

    private final GoogleIdTokenVerifier verifier;

    @Autowired
    private UserReponsitory userReponsitory;  // Inject UserRepository để tìm người dùng
    @Autowired
    private AuthService authService;// Inject AuthService để tạo JWT token

    @NonFinal
    protected static final String SIGNER_KEY = "GtuAkpoXNfZOhcfdgkDJQ+N1Pd1pDwlc0syKYXZPQJT2ZI+mlWkd8Go5XL6rz93j";


    public GoogleService() {
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        NetHttpTransport transport = new NetHttpTransport();

        this.verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("934560233636-oerj7ripv9ituu22ntoc4nn4gcunq180.apps.googleusercontent.com"))
                .build();
    }

    public GoogleResponse verifyGoogleToken(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Kiểm tra người dùng có trong hệ thống hay không
                User existingUser = userReponsitory.findByEmail(payload.getEmail()).orElse(null);

                if (existingUser == null) {
                    // Nếu người dùng chưa tồn tại, tiến hành tạo người dùng mới
                    existingUser = createNewUser(payload);
                }

                // Tạo JWT token cho người dùng
                String generateToken = generateToken(existingUser);

                // Trả về thông tin người dùng cùng với token
                return GoogleResponse.builder()
                        .userId(existingUser.getUserid())
                        .email(existingUser.getEmail())
                        .name(existingUser.getFullname())
                        .token(generateToken)
                        .build();
            } else {
                throw new IllegalArgumentException("Token không hợp lệ");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xác minh Google token: " + e.getMessage(), e);
        }
    }

    // Tạo người dùng mới từ payload của Google
    private User createNewUser(GoogleIdToken.Payload payload) {
        User newUser = new User();
        newUser.setUserid(payload.getSubject());  // Sử dụng Google ID làm userid
        newUser.setEmail(payload.getEmail());
        newUser.setFullname((String) payload.get("name"));
        newUser.setPassword(null);  // Không cần mật khẩu cho người dùng Google
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        newUser.setRole(roles);
        return userReponsitory.save(newUser);
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getFullname())
                .issuer("quydeptrai.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("userid", user.getUserid())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRole()))
            user.getRole().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }
}
