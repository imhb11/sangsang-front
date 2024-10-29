package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

public class Meeting_category extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_categoty);

        //하단바 버튼 작동코드들
        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_category.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_category.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_category.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_category.this, Mypage.class);
            startActivity(intent);
        });

        ////예시
        ListView listView = findViewById(R.id.listView);

        // 데이터 리스트 생성
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};

        // 어댑터 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        // ListView에 어댑터 설정
        listView.setAdapter(adapter);

        // ListView 항목 클릭 시 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭한 항목의 데이터
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Intent를 사용하여 새로운 액티비티로 이동
                Intent intent = new Intent(Meeting_category.this, Meeting_detail.class);
                intent.putExtra("item_name", selectedItem); // 데이터 전달
                startActivity(intent);
            }
        });

        //글 작성
        findViewById(R.id.additional_button).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_category.this, Meeting_write.class);
            startActivity(intent);
        });
    }
}
