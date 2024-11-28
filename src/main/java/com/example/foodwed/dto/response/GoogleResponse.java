package com.example.foodwed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleResponse {
    private String userId;
    private String email;
    private String name;
    private String token;
    private boolean emailVerified;
}
