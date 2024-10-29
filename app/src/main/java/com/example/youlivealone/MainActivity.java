package com.example.youlivealone;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.youlivealone.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 1500; // 뒤로가기 버튼을 누른 시간 간격 (1.5초)
    private long mBackPressedTime; // 뒤로가기 버튼을 누른 시간을 저장할 변수

    private ActivityMainBinding mBinding;
    private Handler sliderHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        // 이미지 슬라이드 코드
        ViewPager2 viewPager2 = mBinding.viewPager;
        List<Integer> images = Arrays.asList(
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4
        );
        ImageAdapter adapter = new ImageAdapter(images, viewPager2);
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000);
            }
        });

        // 버튼 작동 코드들

        mBinding.notice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Notice.class);
            startActivity(intent);
        });

        mBinding.purchase.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Purchase.class);
            startActivity(intent);
        });

        mBinding.meeting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Meeting.class);
            startActivity(intent);
        });

        mBinding.community.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Community.class);
            startActivity(intent);
        });

        mBinding.smart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Smart.class);
            startActivity(intent);
        });


        mBinding.check.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Check.class);
            startActivity(intent);
        });

        mBinding.home.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        mBinding.chat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Chat.class);
            startActivity(intent);
        });

        mBinding.mypage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Mypage.class);
            startActivity(intent);
        });
    }


    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (mBackPressedTime + TIME_INTERVAL > currentTime) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            final Toast toast = Toast.makeText(this, "한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT);
            toast.show();

            new Handler().postDelayed(toast::cancel, TIME_INTERVAL);
        }
        mBackPressedTime = currentTime;
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = mBinding.viewPager.getCurrentItem() + 1;
            if (nextItem >= mBinding.viewPager.getAdapter().getItemCount()) {
                nextItem = 0;
            }
            mBinding.viewPager.setCurrentItem(nextItem, true);
        }
    };


}
