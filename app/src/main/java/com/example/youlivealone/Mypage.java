package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mypage extends AppCompatActivity {

    private TextView nicknameTextView;
    private TextView pointsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        nicknameTextView = findViewById(R.id.nickname);
        pointsTextView = findViewById(R.id.points);

        // ExecutorService를 통해 네트워크 작업을 백그라운드에서 실행
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            try {
                // 네트워크 요청 수행
                URL url = new URL("https://your-api-url.com/userinfo?userid=yourUserId");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                // JSON 파싱
                JSONObject jsonObject = new JSONObject(result.toString());
                String nickname = jsonObject.getString("nickname");
                int points = jsonObject.getInt("points");

                // UI 업데이트 (UI 스레드에서 실행)
                mainHandler.post(() -> {
                    nicknameTextView.setText(nickname);
                    pointsTextView.setText("보유 포인트: " + points + " P");
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 기존 버튼 작동 코드
        findViewById(R.id.map).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, Map.class));
        });

        findViewById(R.id.check).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, Check.class));
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, MainActivity.class));
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, Chat.class));
        });

        findViewById(R.id.mypage).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, Mypage.class));
        });

        // 추가된 버튼 작동 코드
        findViewById(R.id.account_settings).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, AccountSettingsActivity.class));
        });

        findViewById(R.id.notification_settings).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, NotificationSettingsActivity.class));
        });

        // 메뉴 아이템 클릭 리스너 추가
        findViewById(R.id.menu_item_1).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, PurchaseActivity.class));
        });

        findViewById(R.id.menu_item_2).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, GroupActivity.class));
        });

        findViewById(R.id.menu_item_3).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, CommunityActivity.class));
        });

        findViewById(R.id.menu_item_4).setOnClickListener(v -> {
            startActivity(new Intent(Mypage.this, ReviewActivity.class));
        });
    }
}
