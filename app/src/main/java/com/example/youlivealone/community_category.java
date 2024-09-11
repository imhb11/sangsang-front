package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class community_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        //<인기게시글>
        // ListView를 찾기
        ListView hotlist = findViewById(R.id.hotpost);
        // 데이터 소스 생성
        String[] hotdata = {"항목 1", "항목 2", "항목 3", "항목 4", "항목 5"};
        // ArrayAdapter 생성
        ArrayAdapter<String> hotadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hotdata);
        // ListView에 어댑터 설정
        hotlist.setAdapter(hotadapter);
        hotlist.setOnItemClickListener((parent, view, position, id) -> {
            // 선택된 항목의 데이터 가져오기
            String selectedItem = hotdata[position];

            // Intent 생성
            Intent intent = new Intent(community_category.this, MessageBoard.class);//게시판 선택 후 클래스
            // 선택된 항목의 데이터를 전달
            intent.putExtra("selectedItem", selectedItem);

            // Activity 전환
            startActivity(intent);
        });

        //게시판 선택
        LinearLayout talksLayout = findViewById(R.id.talks);

        // LinearLayout 안에 있는 각 ImageButton 가져와서 클릭 리스너 설정
        for (int i = 0; i < talksLayout.getChildCount(); i++) {
            View child = talksLayout.getChildAt(i);
            if (child instanceof ImageButton) {
                ImageButton button = (ImageButton) child;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(community_category.this, MessageBoard.class);
                        startActivity(intent);
                    }
                });
            }
        }




        //<게시글>

        ListView postlist = findViewById(R.id.post);
        String[] postdata = {"항목 1", "항목 2", "항목 3", "항목 4", "항목 5"};
        ArrayAdapter<String> postadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postdata);
        postlist.setAdapter(postadapter);
        // 항목 클릭 리스너 설정
        postlist.setOnItemClickListener((parent, view, position, id) -> {
            // 선택된 항목의 데이터 가져오기
            String selectedItem = postdata[position];

            // Intent 생성
            Intent intent = new Intent(community_category.this, MessageBoard.class);//게시판 선택 후 클래스
            // 선택된 항목의 데이터를 전달
            intent.putExtra("selectedItem", selectedItem);

            // Activity 전환
            startActivity(intent);
        });


        //버튼 작동코드들

        findViewById(R.id.map).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Map.class);
            startActivity(intent);
        });

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Mypage.class);
            startActivity(intent);
        });

    }
}
