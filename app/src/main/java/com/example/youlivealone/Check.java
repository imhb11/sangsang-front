package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youlivealone.Chat;
import com.example.youlivealone.MainActivity;
import com.example.youlivealone.Mypage;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Check extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Map<CalendarDay, String> moodMap = new HashMap<>(); // 날짜와 기분 매핑
    private SharedPreferences sharedPreferences;
    private String jwtToken;  // JWT 토큰을 저장할 변수
    private String memberId = "your_member_id"; // 실제 memberId 값 설정
    private RequestQueue requestQueue;  // Volley의 요청 큐

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);

        calendarView = findViewById(R.id.calendarView);
        sharedPreferences = getSharedPreferences("MoodPreferences", MODE_PRIVATE);

        // JWT 토큰 가져오기 (예시: SharedPreferences에서 가져온다고 가정)
        jwtToken = sharedPreferences.getString("jwt_token", "");

        // 이전에 저장된 이모티콘 상태 복원
        loadMoodsFromPreferences();

        // Volley 요청 큐 초기화
        requestQueue = Volley.newRequestQueue(this);

        // 오늘 날짜 가져오기
        CalendarDay today = CalendarDay.today();

        // 날짜 선택 리스너 설정
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            // 오늘 날짜를 터치했을 때만 이모티콘 선택 다이얼로그 표시
            if (date.equals(today)) {
                showMoodDialog(date);
            } else {
                Toast.makeText(Check.this, "오늘 날짜만 선택 가능합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 버튼 작동 코드들
        findViewById(R.id.map).setOnClickListener(v -> startActivity(new Intent(Check.this, Map.class)));
        findViewById(R.id.check).setOnClickListener(v -> startActivity(new Intent(Check.this, Check.class)));
        findViewById(R.id.home).setOnClickListener(v -> startActivity(new Intent(Check.this, MainActivity.class)));
        findViewById(R.id.chat).setOnClickListener(v -> startActivity(new Intent(Check.this, Chat.class)));
        findViewById(R.id.mypage).setOnClickListener(v -> startActivity(new Intent(Check.this, Mypage.class)));
    }

    // 이모티콘 선택 다이얼로그 표시
    private void showMoodDialog(CalendarDay date) {
        String[] moodEmojis = {"😀 행복", "😐 보통", "😢 슬픔", "😠 화남"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Check.this);
        builder.setTitle("오늘의 기분을 선택하세요")
                .setItems(moodEmojis, (dialog, which) -> {
                    String selectedMood = moodEmojis[which];
                    Toast.makeText(Check.this, "선택된 기분: " + selectedMood, Toast.LENGTH_SHORT).show();

                    // 선택된 기분을 맵에 저장하고 SharedPreferences에 저장
                    moodMap.put(date, selectedMood);
                    saveMoodToPreferences(date, selectedMood);
                    calendarView.addDecorator(new MoodDecorator(date, selectedMood)); // 데코레이터 추가

                    // 기분 선택 후 POST 요청 보내기
                    sendMoodCheckRequest(selectedMood);
                });
        builder.create().show();
    }

    // 기분 체크 POST 요청 보내기
    private void sendMoodCheckRequest(String selectedMood) {
        String url = "http://localhost:8080/check?memberId=" + memberId;

        // JSON 객체 생성
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mood", selectedMood);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // POST 요청 생성
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Check.this, "출석 체크 완료!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Check.this, "출석 체크 실패: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + jwtToken); // JWT 토큰 추가
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // 요청 큐에 추가
        requestQueue.add(jsonObjectRequest);
    }

    // MoodDecorator 클래스
    private class MoodDecorator implements DayViewDecorator {
        private final CalendarDay date;
        private final String mood;

        public MoodDecorator(CalendarDay date, String mood) {
            this.date = date;
            this.mood = mood;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            // 선택된 기분에 따라 이미지 리소스를 설정
            int drawableId = getDrawableForMood(mood);
            if (drawableId != 0) {
                Drawable drawable = ContextCompat.getDrawable(Check.this, drawableId);
                view.setBackgroundDrawable(drawable); // 배경에 이미지 설정
            }
        }

        // 기분에 따라 알맞은 drawable 리소스 ID 반환
        private int getDrawableForMood(String mood) {
            switch (mood) {
                case "😀 행복":
                    return R.drawable.happy;
                case "😐 보통":
                    return R.drawable.just;
                case "😢 슬픔":
                    return R.drawable.sad;
                case "😠 화남":
                    return R.drawable.angry;
                default:
                    return 0; // 기본값 (해당 없을 경우)
            }
        }
    }

    // SharedPreferences에 기분 저장
    private void saveMoodToPreferences(CalendarDay date, String mood) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = date.getYear() + "_" + date.getMonth() + "_" + date.getDay();
        editor.putString(key, mood);
        editor.apply();
    }

    // SharedPreferences에서 기분 복원
    private void loadMoodsFromPreferences() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String[] dateParts = entry.getKey().split("_");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);
            CalendarDay date = CalendarDay.from(year, month, day);
            String mood = (String) entry.getValue();

            // 복원된 기분을 맵에 저장하고 데코레이터 추가
            moodMap.put(date, mood);
            calendarView.addDecorator(new MoodDecorator(date, mood));
        }
    }
}
