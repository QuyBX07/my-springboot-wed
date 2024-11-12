package com.example.foodwed.exception;

public enum ErrorCode {
    USSER_EXITED(1001, "Email da ton tai","fail"),
    USSER_Email(1002,"Email needs to be in the correct format","fail"),
    USER_EMAIL_Error(1003,"email not correct","fail"),
    PASSWORD_NOT_CORECT(1004,"password not correct","fail"),
    UNAAUTHENTICATED(1006, "kiem tra lai mat khau va email","fail")
    ;


    ErrorCode(int code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    private int code;
    private String message;
    private String status;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
