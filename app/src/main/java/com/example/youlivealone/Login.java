package com.example.youlivealone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    TextView sign;
    private static final int TIME_INTERVAL = 1500; // 뒤로가기 버튼을 누른 시간 간격 (1.5초)
    private long mBackPressedTime; // 뒤로가기 버튼을 누른 시간을 저장할 변수

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.login);

        //회원가입 버튼 누르면 회원가입 페이지로 이동
        findViewById(R.id.signin).setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        // 로그인 버튼 누르면 메인화면으로 이동
        findViewById(R.id.loginbutton).setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        });
    }
    public void onBackPressed() {
        // 현재 시간 가져옴
        long currentTime = System.currentTimeMillis();

        // 뒤로가기 버튼을 처음 눌렀을 때
        if (mBackPressedTime + TIME_INTERVAL > currentTime) {
            super.onBackPressed();
        } else { // 뒤로가기 버튼 처음 눌렀거나 시간 간격을 초과
            Intent intent = new Intent(Login.this, Login.class);
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