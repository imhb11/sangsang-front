package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;

public class MessageBoard extends AppCompatActivity {

    private EditText searchBar;
    private ImageButton searchButton, additionalButton;
    private Button latestButton, popularButton;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messageboard); // messageboard.xml 레이아웃 파일 참조

        // UI 요소를 findViewById로 연결
        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);
        additionalButton = findViewById(R.id.additional_button);
        latestButton = findViewById(R.id.latest_button);
        popularButton = findViewById(R.id.popular_button);
        scrollView = findViewById(R.id.scrollView);

        // 검색 버튼 클릭 이벤트 처리
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchBar.getText().toString();
                // 검색 기능 구현 (예: 검색어로 필터링된 게시물 표시)
            }
        });

        // 추가 버튼 클릭 이벤트 처리
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageBoard.this, AddMessageBoard.class);
                startActivity(intent);
                // 추가 기능 구현 (예: 새로운 게시물 추가)
            }
        });

        // 최신순 버튼 클릭 이벤트 처리
        latestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 최신순 정렬 기능 구현
            }
        });

        // 인기순 버튼 클릭 이벤트 처리
        popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인기순 정렬 기능 구현
            }
        });
    }
}