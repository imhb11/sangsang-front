package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class Mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        // 기존 버튼 작동 코드
        findViewById(R.id.map).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, Map.class);
            startActivity(intent);
        });

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, Chat.class);
            startActivity(intent);
        });

        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, Mypage.class);
            startActivity(intent);
        });

        // 추가된 버튼 작동 코드
        findViewById(R.id.account_settings).setOnClickListener(v -> {
            // 계정 설정 화면으로 이동
            Intent intent = new Intent(Mypage.this, AccountSettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.notification_settings).setOnClickListener(v -> {
            // 알림 설정 화면으로 이동
            Intent intent = new Intent(Mypage.this, NotificationSettingsActivity.class);
            startActivity(intent);
        });
    }
}
