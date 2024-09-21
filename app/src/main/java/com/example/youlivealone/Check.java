package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashMap;
import java.util.Map;

public class Check extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Map<CalendarDay, String> moodMap = new HashMap<>(); // 날짜와 기분 매핑
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);

        calendarView = findViewById(R.id.calendarView);
        sharedPreferences = getSharedPreferences("MoodPreferences", MODE_PRIVATE);

        // 이전에 저장된 이모티콘 상태 복원
        loadMoodsFromPreferences();

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
                });
        builder.create().show();
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