package com.example.youlivealone;

import java.io.Serializable;

public class Post implements Serializable {
    private int userid;
    private String title;
    private String content;

    public Post(int userId, String title, String content) {
        this.userid = userId;
        this.title = title;
        this.content = content;
    }

    // Getters
    public int getUserId() {
        return userid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title; // 또는 필요한 내용을 반환
    }
}
