package com.example.youlivealone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddMessageBoard extends AppCompatActivity {

    private EditText titleInput, subtitleInput, contentInput;
    private Button uploadButton, saveButton, keywordButton, fileButton, mp3Button, emojiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmessageboard); // addmessageboard.xml 레이아웃 파일 참조

        // UI 요소를 findViewById로 연결
        titleInput = findViewById(R.id.title_input);
        subtitleInput = findViewById(R.id.subtitle_input);
        contentInput = findViewById(R.id.content_input);
        uploadButton = findViewById(R.id.upload_button);
        saveButton = findViewById(R.id.save_button);
        keywordButton = findViewById(R.id.keyword_button);
        fileButton = findViewById(R.id.file_button);
        mp3Button = findViewById(R.id.mp3_button);
        emojiButton = findViewById(R.id.emoji_button);

        // 업로드 버튼 클릭 이벤트 처리
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String subtitle = subtitleInput.getText().toString();
                String content = contentInput.getText().toString();
                // 업로드 기능 구현
            }
        });

        // 임시저장 버튼 클릭 이벤트 처리
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String subtitle = subtitleInput.getText().toString();
                String content = contentInput.getText().toString();
                // 임시저장 기능 구현
            }
        });

        // 키워드 버튼 클릭 이벤트 처리
        keywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 키워드 추가 기능 구현
            }
        });

        // 파일 버튼 클릭 이벤트 처리
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 파일 추가 기능 구현
            }
        });

        // Mp3 버튼 클릭 이벤트 처리
        mp3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mp3 추가 기능 구현
            }
        });

        // 이모지 버튼 클릭 이벤트 처리
        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이모지 추가 기능 구현
            }
        });
    }
}