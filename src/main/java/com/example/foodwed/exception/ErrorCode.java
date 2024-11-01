package com.example.foodwed.exception;

public enum ErrorCode {
    USSER_EXITED(1001, "User Existed"),
    USSER_Email(1002,"Email needs to be in the correct format"),
    USER_EMAIL_Error(1003,"email not correct"),
    PASSWORD_NOT_CORECT(1004,"password not correct")
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
