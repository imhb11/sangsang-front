package com.example.youlivealone;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.style.ReplacementSpan;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashMap;
import java.util.Map;

public class Check extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Map<CalendarDay, String> moodMap = new HashMap<>(); // 날짜와 기분 매핑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);

        calendarView = findViewById(R.id.calendarView);

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

                    // 선택된 기분을 맵에 저장
                    moodMap.put(date, selectedMood);
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
            view.addSpan(new CustomTextSpan(mood)); // 이모티콘을 텍스트로 표시
        }
    }

    // CustomTextSpan 클래스
    public class CustomTextSpan extends ReplacementSpan {
        private final String emoji;

        public CustomTextSpan(String emoji) {
            this.emoji = emoji;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return (int) paint.measureText(emoji); // 이모티콘의 너비 계산
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            canvas.drawText(emoji, x, y, paint); // 이모티콘 그리기
        }
    }
}