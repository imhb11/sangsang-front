package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        // Back 버튼 클릭 시 Mypage로 돌아가기
        TextView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(GroupActivity.this, Mypage.class);
            startActivity(intent);
            finish(); // 현재 Activity 종료하여 돌아가기
        });
        // 기존 버튼 작동 코드
        findViewById(R.id.check).setOnClickListener(v -> {
            startActivity(new Intent(GroupActivity.this, Check.class));
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            startActivity(new Intent(GroupActivity.this, MainActivity.class));
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            startActivity(new Intent(GroupActivity.this, Chat.class));
        });

        findViewById(R.id.mypage).setOnClickListener(v -> {
            startActivity(new Intent(GroupActivity.this, Mypage.class));
        });
    }
}
