package com.example.foodwed.exception;

public enum ErrorCode {

    USSER_EXITED("fail",1001, "User Existed"),
    USSER_Email("fail",1002,"Email needs to be in the correct format"),
    USER_EMAIL_Error("fail",1003,"email not correct"),
    PASSWORD_NOT_CORECT("fail",1004,"password not correct"),
    SUGGESTION_ERROR("fail",1005,"not found suggestion"),
    PARAM_ERROR("fail", 1006,"missing parameter"),
    UNAAUTHENTICATED("fail",1010, "kiem tra lai mat khau va email")
    ;
    ErrorCode(String status,int code, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private int code;
    private String message;
    private String status;

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
