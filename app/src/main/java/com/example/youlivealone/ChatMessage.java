// ChatMessage.java
package com.example.youlivealone;

public class ChatMessage {
    private String userId;  // 유저 ID
    private String message; // 메시지 내용
    private String timestamp; // 메시지 전송 시간

    public ChatMessage(String userId, String message, String timestamp) {
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
