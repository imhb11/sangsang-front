package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Meeting_detail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_detail);

        // 전달받은 데이터 추출
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("item_name");

    }

}
