package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // Back 버튼 클릭 시 Mypage로 돌아가기
        TextView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CommunityActivity.this, Mypage.class);
            startActivity(intent);
            finish(); // 현재 Activity 종료하여 돌아가기
        });
        // 기존 버튼 작동 코드
        findViewById(R.id.check).setOnClickListener(v -> {
            startActivity(new Intent(CommunityActivity.this, Check.class));
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            startActivity(new Intent(CommunityActivity.this, MainActivity.class));
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            startActivity(new Intent(CommunityActivity.this, Chat.class));
        });

        findViewById(R.id.mypage).setOnClickListener(v -> {
            startActivity(new Intent(CommunityActivity.this, Mypage.class));
        });
    }
}
