package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import com.example.youlivealone.RoomViewModel;



public class Community extends AppCompatActivity {
    private RoomViewModel roomViewModel;
    private GridLayout popularGrid;
    private GridLayout categoryGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        popularGrid = findViewById(R.id.populargrid);
        categoryGrid = findViewById(R.id.categorygrid);

        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        roomViewModel.getRoomList().observe(this, new Observer<List<String>>() {

            @Override
            public void onChanged(List<String> rooms) {
                updateGridLayout(popularGrid, rooms, 2);
            }
        });

        roomViewModel.getRoomList1().observe(this, new Observer<List<String>>() {

            @Override
            public void onChanged(List<String> rooms) {
                updateGridLayout(categoryGrid, rooms, 2);
            }
        });


        //버튼 작동코드들

        findViewById(R.id.map).setOnClickListener(v -> {
            Intent intent = new Intent(Community.this, Map.class);
            startActivity(intent);
        });

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
    }

    private void updateGridLayout(GridLayout gridLayout, List<String> rooms, int columns) {
        gridLayout.removeAllViews();  // 기존 버튼 제거

        for (int i = 0; i < rooms.size(); i++) {
            String roomName = rooms.get(i);

            Button button = new Button(this);
            button.setText(roomName);
            button.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(i / columns), GridLayout.spec(i % columns)));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 버튼 클릭 시의 동작 정의
                    Intent intent = new Intent(Community.this, community_category.class);
                    startActivity(intent);
                }
            });

            gridLayout.addView(button);
        }
    }
}