package com.example.youlivealone;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    TextView back;
    EditText name, ID, PW, pw2, email, birthyear, birthdate, birthday;
    Button pwcheck, submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Find views by their ID
        submit = findViewById(R.id.signupbutton);
        ID = findViewById(R.id.signID);
        PW = findViewById(R.id.signPW);
        back = findViewById(R.id.back);
        name = findViewById(R.id.signName);
        pw2 = findViewById(R.id.signPW2);
        email = findViewById(R.id.signmail);
        birthyear = findViewById(R.id.signBirth);
        birthdate = findViewById(R.id.signBirth2);
        birthday = findViewById(R.id.signBirth3);
        pwcheck = findViewById(R.id.pwcheckbutton);

        // Back button click event
        back.setOnClickListener(v -> onBackPressed());

        // Password check button click event
        pwcheck.setOnClickListener(v -> {
            if (PW.getText().toString().equals(pw2.getText().toString())) {
                pwcheck.setText("일치");
            } else {
                Toast.makeText(Register.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        // Submit button click event
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString();
                String pw = PW.getText().toString();
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Birthyear = birthyear.getText().toString();
                String Birthdate = birthdate.getText().toString();
                String Birthday = birthday.getText().toString();
                String fullBirthdate = Birthyear + "-" + Birthdate + "-" + Birthday;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON 예외 발생", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "예외 발생", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                SignupRequestActivity signupRequestActivity = new SignupRequestActivity(Name, id, fullBirthdate, pw, Email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(signupRequestActivity);
            }
        });
    }
}
