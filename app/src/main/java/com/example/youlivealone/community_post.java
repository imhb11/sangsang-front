package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class community_post extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_post);


        TextView titleView = findViewById(R.id.title);
        TextView contentView = findViewById(R.id.post_content);
        TextView userIdView = findViewById(R.id.userid);

        Intent intent = getIntent();
        Post selectedPost = (Post) intent.getSerializableExtra("selectedPost");

        // 데이터 설정
        titleView.setText(selectedPost.getTitle());
        userIdView.setText(String.valueOf(selectedPost.getUserId())); // 사용자 ID 출력
        contentView.setText(selectedPost.getContent()); // 내용 출력
    }
}
