package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText ID, Password;
    Button Login;
    TextView Signup;
    private static final int TIME_INTERVAL = 1500; // 뒤로가기 버튼을 누른 시간 간격 (1.5초)
    private long mBackPressedTime; // 뒤로가기 버튼을 누른 시간을 저장할 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ID = findViewById(R.id.editID);
        Password = findViewById(R.id.ediPassword);
        Login = findViewById(R.id.loginbutton);
        Signup = findViewById(R.id.signin);

        //작업용 로그인 통과버튼. 완료 후엔 해당 블록 삭제할 것.
        Login.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        });

        /* 로그인 버튼 이벤트
        Login.setOnClickListener(view -> {
            String id = ID.getText().toString().trim();
            String pw = Password.getText().toString().trim();

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://54.79.1.3:8080/members/login";

            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("id", id);
                jsonBody.put("pw", pw);

                String requestParam = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        url,
                        response -> {
                            try {
                                // 서버에서 응답으로 true 또는 false만 보내는 경우 처리
                                boolean success = Boolean.parseBoolean(response.trim());

                                if (success) {
                                    // 로그인 성공 시 토큰을 받는 경우에 대비하여 처리
                                    // 이 부분은 서버에서 실제로 토큰을 반환할 때 사용합니다.
                                    // 예를 들어, 서버에서 "true" 또는 "false"만 반환하면 이 코드는 필요 없습니다.
                                    // 이 예제에서는 서버가 "true"만 반환하고, 실제 토큰이 없다고 가정합니다.

                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userID", id);
                                    editor.apply();

                                    // 메인 액티비티로 이동
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // 로그인 액티비티 종료
                                } else {
                                    Toast.makeText(getApplicationContext(), "로그인 실패: 아이디 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "응답 처리 중 예외 발생: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Log.e("LoginError", "서버 요청 실패: " + error.toString());  // 에러 로그
                            Toast.makeText(getApplicationContext(), "서버 요청 실패: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ) {
                    @Override
                    public byte[] getBody() {
                        return requestParam == null ? null : requestParam.getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };

                // 설정된 타임아웃 값을 10초로 변경
                int timeoutMs = 10000; // 10초
                RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutMs, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "JSON 구성 중 예외 발생: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        */

        // 회원가입 버튼 누르면 회원가입 페이지로 이동
        Signup.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (mBackPressedTime + TIME_INTERVAL > currentTime) {
            super.onBackPressed();
        } else {
            Toast toast = Toast.makeText(this, "한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT);
            toast.show();

            new Handler().postDelayed(toast::cancel, TIME_INTERVAL);
        }

        mBackPressedTime = currentTime;
    }
}