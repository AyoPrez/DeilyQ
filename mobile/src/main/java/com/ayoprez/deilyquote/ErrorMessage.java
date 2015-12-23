package com.ayoprez.deilyquote;

/**
 * Created by ayo on 01.12.15.
 */
public class ErrorMessage {

    private String Message;

    public ErrorMessage(String message){
        this.Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
