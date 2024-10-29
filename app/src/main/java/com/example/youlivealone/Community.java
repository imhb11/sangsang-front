package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import androidx.gridlayout.widget.GridLayout;


public class Community extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);


        //버튼 작동코드들
        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(Community.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(Community.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(Community.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(Community.this, Mypage.class);
            startActivity(intent);
        });

        GridLayout populargridLayout = findViewById(R.id.populargrid);
        for(int i = 0; i < populargridLayout.getChildCount(); i++) {
            View child = populargridLayout.getChildAt(i);
            if (child instanceof ImageButton) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Community.this, Chat.class);
                        // 각 버튼에 맞는 데이터를 인텐트에 추가
                        intent.putExtra("buttonId", v.getId());
                        startActivity(intent);
                    }
                });
            }
        }


        GridLayout categorygridLayout = findViewById(R.id.categorygrid);
        for(int i = 0; i < categorygridLayout.getChildCount(); i++) {
            View child = categorygridLayout.getChildAt(i);
            if (child instanceof ImageButton) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Community.this, community_category.class);
                        // 각 버튼에 맞는 데이터를 인텐트에 추가
                        intent.putExtra("buttonId", v.getId());
                        startActivity(intent);
                    }
                });
            }
        }
    }


}