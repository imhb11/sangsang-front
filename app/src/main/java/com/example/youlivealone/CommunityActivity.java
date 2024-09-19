package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // 작성한 게시글 확인 버튼 클릭 리스너
        findViewById(R.id.button_view_posts).setOnClickListener(v -> {
            Intent intent = new Intent(CommunityActivity.this, ViewPostsActivity.class);
            startActivity(intent);
        });

        // 작성한 댓글 확인 버튼 클릭 리스너
        findViewById(R.id.button_view_comments).setOnClickListener(v -> {
            Intent intent = new Intent(CommunityActivity.this, ViewCommentsActivity.class);
            startActivity(intent);
        });

        // 공감한 게시글 확인 버튼 클릭 리스너
        findViewById(R.id.button_view_liked_posts).setOnClickListener(v -> {
            Intent intent = new Intent(CommunityActivity.this, ViewLikedPostsActivity.class);
            startActivity(intent);
        });

        // 참여하는 채팅방 확인 버튼 클릭 리스너
        findViewById(R.id.button_view_chat_rooms).setOnClickListener(v -> {
            Intent intent = new Intent(CommunityActivity.this, ViewChatRoomsActivity.class);
            startActivity(intent);
        });
    }
}
