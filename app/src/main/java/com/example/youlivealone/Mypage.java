package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        //버튼 작동코드들

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
    }
}