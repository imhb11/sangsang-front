package com.example.youlivealone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountSettingsActivity extends AppCompatActivity {

    private EditText nicknameEditText;
    private EditText emailEditText;
    private EditText emergencyContactEditText;
    private Button saveNicknameButton;
    private Button saveEmailButton;
    private Button saveEmergencyContactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // UI 요소 연결
        nicknameEditText = findViewById(R.id.edit_nickname);
        emailEditText = findViewById(R.id.edit_email);
        emergencyContactEditText = findViewById(R.id.edit_emergency_contact);
        saveNicknameButton = findViewById(R.id.button_save_nickname);
        saveEmailButton = findViewById(R.id.button_save_email);
        saveEmergencyContactButton = findViewById(R.id.button_save_emergency_contact);

        // 닉네임 저장 버튼 클릭 리스너
        saveNicknameButton.setOnClickListener(v -> {
            String newNickname = nicknameEditText.getText().toString();
            if (!newNickname.isEmpty()) {
                saveNickname(newNickname);
            } else {
                Toast.makeText(AccountSettingsActivity.this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 이메일 저장 버튼 클릭 리스너
        saveEmailButton.setOnClickListener(v -> {
            String newEmail = emailEditText.getText().toString();
            if (!newEmail.isEmpty() && newEmail.contains("@")) {
                saveEmail(newEmail);
            } else {
                Toast.makeText(AccountSettingsActivity.this, "유효한 이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 비상연락처 저장 버튼 클릭 리스너
        saveEmergencyContactButton.setOnClickListener(v -> {
            String newContact = emergencyContactEditText.getText().toString();
            if (!newContact.isEmpty()) {
                saveEmergencyContact(newContact);
            } else {
                Toast.makeText(AccountSettingsActivity.this, "비상 연락처를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 닉네임 저장 함수 (예시로 토스트 메시지를 출력함, 실제로는 서버나 DB에 저장해야 함)
    private void saveNickname(String nickname) {
        // 실제 저장 로직을 여기에 구현
        Toast.makeText(this, "닉네임이 변경되었습니다: " + nickname, Toast.LENGTH_SHORT).show();
    }

    // 이메일 저장 함수 (예시로 토스트 메시지를 출력함, 실제로는 서버나 DB에 저장해야 함)
    private void saveEmail(String email) {
        // 실제 저장 로직을 여기에 구현
        Toast.makeText(this, "이메일이 변경되었습니다: " + email, Toast.LENGTH_SHORT).show();
    }

    // 비상 연락처 저장 함수 (예시로 토스트 메시지를 출력함, 실제로는 서버나 DB에 저장해야 함)
    private void saveEmergencyContact(String contact) {
        // 실제 저장 로직을 여기에 구현
        Toast.makeText(this, "비상 연락처가 변경되었습니다: " + contact, Toast.LENGTH_SHORT).show();
    }
}
