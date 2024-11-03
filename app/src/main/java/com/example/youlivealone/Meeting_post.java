package com.example.youlivealone;

import java.io.Serializable;

public class Meeting_post implements Serializable {
    private String id; // 글 ID
    private String title; // 글 제목
    private String introduction; // 소개
    private String content; // 내용
    private int memberCount; // 멤버 수
    private String subcategoryID; // 서브카테고리 ID
    private String meetingCategoryId; // 회의 카테고리 ID

    // 생성자
    public Meeting_post(String id, String title, String introduction, String content, int memberCount, String subcategoryID, String meetingCategoryId) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.content = content;
        this.memberCount = memberCount;
        this.subcategoryID = subcategoryID;
        this.meetingCategoryId = meetingCategoryId;
    }

    // Getter 메서드
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getContent() {
        return content;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public String getMeetingCategoryId() {
        return meetingCategoryId;
    }

    // toString 메서드 (옵션)
    @Override
    public String toString() {
        return title; // ListView에서 보여줄 텍스트
    }
}
