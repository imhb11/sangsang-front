package com.example.youlivealone;

//채팅방 생성시에만 사용
public class ChatRoom {
    private String name;
    private String description;
    private int maxParticipants;
    private String category;
    private int participantCount; // 현재 채팅방 인원
    private String lastMessage;   // 마지막 메시지
    private String lastMessageTime;    // 마지막 메시지 시간

    public ChatRoom(String name, String description, int maxParticipants, String category) {
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.category = category;


        this.participantCount = 0;  // 초기화
        this.lastMessage = "";      // 초기화
        this.lastMessageTime = "";    // 초기화
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getMaxParticipants() { return maxParticipants; }
    public String getCategory() { return category; }
    public int getParticipantCount() { return participantCount; }
    public String getLastMessage() { return lastMessage; }
    public String getLastMessageTime() { return lastMessageTime; }

    public void setParticipantCount(int participantCount) { this.participantCount = participantCount; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public void setLastMessageTime(String lastMessageTime) { this.lastMessageTime = lastMessageTime; }
}
