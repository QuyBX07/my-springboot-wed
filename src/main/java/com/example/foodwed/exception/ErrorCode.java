package com.example.foodwed.exception;

public enum ErrorCode {
<<<<<<< HEAD
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
=======
    USSER_EXITED("fail",1001, "User Existed"),
    USSER_Email("fail",1002,"Email needs to be in the correct format"),
    USER_EMAIL_Error("fail",1003,"email not correct"),
    PASSWORD_NOT_CORECT("fail",1004,"password not correct"),
    SUGGESTION_ERROR("fail",1005,"not found suggestion"),
    PARAM_ERROR("fail", 1006,"missing parameter")
    ;
    ErrorCode(String status,int code, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
>>>>>>> cf1313390f71f96720bf92ff14226d945e5e1842
    }

    private int code;
    private String message;
<<<<<<< HEAD
    private String status;
=======
private String status;

    public String getStatus() {
        return status;
    }
>>>>>>> cf1313390f71f96720bf92ff14226d945e5e1842

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
