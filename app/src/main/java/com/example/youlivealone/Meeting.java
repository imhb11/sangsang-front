package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class Meeting extends AppCompatActivity {
    private GridLayout meeting_categorygrid;
    private RoomViewModel roomViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting);

        //하단바 버튼 작동코드들

        findViewById(R.id.map).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Map.class);
            startActivity(intent);
        });

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Mypage.class);
            startActivity(intent);
        });

        //카테고리 버튼 클릭 시
        GridLayout gatheringCategoryLayout = findViewById(R.id.gathering_category);
        for (int i = 0; i < gatheringCategoryLayout.getChildCount(); i++) {
            View child = gatheringCategoryLayout.getChildAt(i);
            if (child instanceof ImageButton) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Meeting.this, Meeting_category.class);
                        // 각 버튼에 맞는 데이터를 인텐트에 추가
                        intent.putExtra("buttonId", v.getId());
                        startActivity(intent);
                    }
                });
            }
        }
    }



}
