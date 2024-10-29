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

        // 닉네임 가져오기
        executorService.execute(() -> {
            try {
                // 닉네임 조회 API 호출
                URL nicknameUrl = new URL("http://54.79.1.3:8080/mypage/nickname/{memberId}");
                HttpURLConnection nicknameConnection = (HttpURLConnection) nicknameUrl.openConnection();
                nicknameConnection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(nicknameConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                // JSON 파싱
                JSONObject nicknameJson = new JSONObject(result.toString());
                String nickname = nicknameJson.getString("nickname");

                // UI 업데이트 (UI 스레드에서 실행)
                mainHandler.post(() -> {
                    nicknameTextView.setText(nickname);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 포인트 가져오기
        executorService.execute(() -> {
            try {
                // 포인트 조회 API 호출
                URL pointsUrl = new URL("http://54.79.1.3:8080/mypage/points/{memberId}");
                HttpURLConnection pointsConnection = (HttpURLConnection) pointsUrl.openConnection();
                pointsConnection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(pointsConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                // JSON 파싱
                JSONObject pointsJson = new JSONObject(result.toString());
                int points = pointsJson.getInt("points");

                // UI 업데이트 (UI 스레드에서 실행)
                mainHandler.post(() -> {
                    pointsTextView.setText("보유 포인트: " + points + " P");
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 기존 버튼 작동 코드
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
