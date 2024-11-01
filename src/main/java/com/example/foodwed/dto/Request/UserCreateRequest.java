package com.example.foodwed.dto.Request;

import jakarta.validation.constraints.Email;

public class UserCreateRequest {
    private String fullname;
    private String password;
    @Email(message = "USSER_Email")
    private String email;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
