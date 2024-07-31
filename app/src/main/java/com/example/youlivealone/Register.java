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
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Register extends AppCompatActivity {
    TextView back;
    EditText name,ID,PW,pw2,email,birthyear,birthdate,birthday;
    Button pwcheck, submit, signupbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        submit = findViewById(R.id.signupbutton);
        ID = (EditText) findViewById(R.id.signID);
        PW = (EditText) findViewById(R.id.signPW);

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed() );

        //기입 항목
        name = findViewById(R.id.signName);
        pw2=findViewById(R.id.signPW2);
        email=findViewById(R.id.signmail);
        birthyear=findViewById(R.id.signBirth);
        birthdate=findViewById(R.id.signBirth2);
        birthday=findViewById(R.id.signBirth3);

        //비밀번호 확인 버튼
        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(v -> {
            if(PW.getText().toString().equals(pw2.getText().toString())){
                pwcheck.setText("일치");
            }else{
                Toast.makeText(Register.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        //회원가입 버튼 이벤트
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString();
                String pw = PW.getText().toString();
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Birthdate = birthdate.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch(JSONException e)
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

                SignupRequestActivity signupRequestActivity = new SignupRequestActivity(Name, id, Birthdate, pw, Email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(signupRequestActivity);
            }
        });



        //회원가입 완료 버튼

        submit.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });



    }
}