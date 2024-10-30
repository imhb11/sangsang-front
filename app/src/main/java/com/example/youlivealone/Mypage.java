package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.JWT;

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
    private String memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        nicknameTextView = findViewById(R.id.nickname);
        pointsTextView = findViewById(R.id.points);

        findViewById(R.id.settings_icon).setOnClickListener(v -> openSettingsScreen());
        findViewById(R.id.menu_item_community).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, CommunityActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.menu_item_group).setOnClickListener(v -> {
            Intent intent = new Intent(Mypage.this, GroupActivity.class);
            startActivity(intent);
        });


        // SharedPreferences에서 JWT 토큰 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwtToken", null);

        if (token != null) {
            Log.d("JWTToken", "Loaded JWT Token: " + token);

            // JWT 객체 생성
            JWT jwt = new JWT(token);

            // "sub" 클레임을 memberId로 사용
            memberId = jwt.getClaim("sub").asString();
            Log.d("Mypage", "Extracted memberId (from sub): " + memberId);
        } else {
            Log.e("Mypage", "JWT Token is null");
        }

        // ExecutorService를 통해 네트워크 작업을 백그라운드에서 실행
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        // 닉네임 가져오기
        executorService.execute(() -> {
            if (memberId != null) {
                try {
                    // 닉네임 조회 API 호출
                    URL nicknameUrl = new URL("http://15.165.92.121:8080/mypage/nickname/" + memberId);
                    HttpURLConnection nicknameConnection = (HttpURLConnection) nicknameUrl.openConnection();
                    nicknameConnection.setRequestMethod("GET");

                    int responseCode = nicknameConnection.getResponseCode();
                    if (responseCode == 200) { // 성공 응답일 경우
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
                        mainHandler.post(() -> nicknameTextView.setText(nickname));
                    } else {
                        Log.e("NicknameAPI", "Failed to fetch nickname: " + responseCode);
                    }

                } catch (Exception e) {
                    Log.e("NicknameError", "Error fetching nickname", e);
                }
            } else {
                Log.e("Mypage", "memberId is null, cannot fetch nickname.");
            }
        });

        // 포인트 가져오기
        executorService.execute(() -> {
            if (memberId != null) {
                try {
                    // 포인트 조회 API 호출
                    URL pointsUrl = new URL("http://15.165.92.121:8080/mypage/points/" + memberId);
                    HttpURLConnection pointsConnection = (HttpURLConnection) pointsUrl.openConnection();
                    pointsConnection.setRequestMethod("GET");

                    int responseCode = pointsConnection.getResponseCode();
                    if (responseCode == 200) { // 성공 응답일 경우
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
                        mainHandler.post(() -> pointsTextView.setText("보유 포인트: " + points + " P"));
                    } else {
                        Log.e("PointsAPI", "Failed to fetch points: " + responseCode);
                    }

                } catch (Exception e) {
                    Log.e("PointsError", "Error fetching points", e);
                }
            } else {
                Log.e("Mypage", "memberId is null, cannot fetch points.");
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
    }
    //설정이동 버튼
    private void openSettingsScreen() {
        Intent intent = new Intent(Mypage.this, SettingActivity.class);
        startActivity(intent);
    }
}
