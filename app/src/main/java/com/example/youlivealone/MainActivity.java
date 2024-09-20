package com.example.youlivealone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 1500; // 뒤로가기 버튼을 누른 시간 간격 (1.5초)
    private long mBackPressedTime; // 뒤로가기 버튼을 누른 시간을 저장할 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDailyNotification();

        //이미지 슬라이드 코드
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        List<Integer> images = Arrays.asList(
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4
        );

        viewPager.setAdapter(new ImageAdapter(images));


        //이미지 슬라이드 코드 종료


        //버튼 작동코드들

        findViewById(R.id.setting).setOnClickListener(v -> {
            Intent goSetting = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(goSetting);
        });

        findViewById(R.id.notice).setOnClickListener(v -> {
            Intent goNotice = new Intent(MainActivity.this, Notice.class);
            startActivity(goNotice);
        });

        findViewById(R.id.honeytip).setOnClickListener(v -> {
            Intent goHoneytip = new Intent(MainActivity.this, Honeytip.class);
            startActivity(goHoneytip);
        });

        findViewById(R.id.purchase).setOnClickListener(v -> {
            Intent goPurchase = new Intent(MainActivity.this, Purchase.class);
            startActivity(goPurchase);
        });

        findViewById(R.id.meeting).setOnClickListener(v -> {
            Intent goMeeting = new Intent(MainActivity.this, Meeting.class);
            startActivity(goMeeting);
        });

        findViewById(R.id.community).setOnClickListener(v -> {
            Intent goCommunity = new Intent(MainActivity.this, Community.class);
            startActivity(goCommunity);
        });

        findViewById(R.id.smart).setOnClickListener(v -> {
            Intent goSmart = new Intent(MainActivity.this, Smart.class);
            startActivity(goSmart);
        });

        findViewById(R.id.recycle).setOnClickListener(v -> {
            Intent goRecycle = new Intent(MainActivity.this, Recycle.class);
            startActivity(goRecycle);
        });

        findViewById(R.id.map).setOnClickListener(v -> {
            Intent goMap = new Intent(MainActivity.this, Map.class);
            startActivity(goMap);
        });

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent goCheck = new Intent(MainActivity.this, Check.class);
            startActivity(goCheck);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent goHome = new Intent(MainActivity.this, MainActivity.class);
            startActivity(goHome);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent goChat = new Intent(MainActivity.this, Chat.class);
            startActivity(goChat);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent goMypage = new Intent(MainActivity.this, Mypage.class);
            startActivity(goMypage);
        });
    }

    private void setDailyNotification() {
        // AlarmManager 인스턴스 생성
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // 알림을 보낼 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 7);
        calendar.set(Calendar.SECOND, 0);



        // 만약 지정된 시간이 오늘의 시간보다 이른 경우, 다음 날로 설정
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // 알림을 실행할 BroadcastReceiver 설정
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 매일 알람 설정
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);


        Toast.makeText(this, "매일 오전 8시에 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        // 현재 시간 가져옴
        long currentTime = System.currentTimeMillis();

        // 뒤로가기 버튼을 처음 눌렀을 때
        if (mBackPressedTime + TIME_INTERVAL > currentTime) {
            super.onBackPressed();
        } else { // 뒤로가기 버튼 처음 눌렀거나 시간 간격을 초과
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            final Toast toast = Toast.makeText(this, "한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT);
            toast.show();

            //TIME_INTERVAL 만큼 토스트 띄우기
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, TIME_INTERVAL);
        }
        // 뒤로가기 버튼 누른 시간을 업데이트
        mBackPressedTime = currentTime;
    }
}