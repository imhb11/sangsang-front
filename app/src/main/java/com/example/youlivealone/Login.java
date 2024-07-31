package com.example.youlivealone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {
    EditText ID, Password;
    Button Login;
    TextView sign, Signup;
    private static final int TIME_INTERVAL = 1500; // 뒤로가기 버튼을 누른 시간 간격 (1.5초)
    private long mBackPressedTime; // 뒤로가기 버튼을 누른 시간을 저장할 변수

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.login);

        ID = findViewById(R.id.editID);
        Password = findViewById(R.id.ediPassword);
        Login = findViewById(R.id.loginbutton);
        Signup = findViewById(R.id.signin);

        //로그인 버튼 이벤트
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString();
                String pw = Password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                String msg = jsonObject.getString("ID");
                                Toast.makeText(getApplicationContext(), "로그인 성공. ID :" + msg, Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequestActivity loginRequestActivity = new LoginRequestActivity(id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(loginRequestActivity);
            }
        });

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