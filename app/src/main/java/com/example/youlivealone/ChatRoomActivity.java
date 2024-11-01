// ChatRoomActivity.java
package com.example.youlivealone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private RecyclerView chatMessagesRecyclerView;
    private ChatMessageAdapter chatMessageAdapter;
    private ArrayList<ChatMessage> chatMessages;
    private EditText messageInput;
    private ImageButton sendButton;
    private String userId; // 유저 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        String chatRoomName = getIntent().getStringExtra("chatRoomName");
        TextView chatRoomTitle = findViewById(R.id.chatRoomTitle);
        chatRoomTitle.setText(chatRoomName);

        chatMessagesRecyclerView = findViewById(R.id.chatMessagesRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        chatMessages = new ArrayList<>();
        chatMessageAdapter = new ChatMessageAdapter(chatMessages);
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatMessagesRecyclerView.setAdapter(chatMessageAdapter);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();

        if (messageText.isEmpty()) {
            Toast.makeText(this, "메시지를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = "보낸 시간"; // 실제 시간값으로 대체
        chatMessages.add(new ChatMessage(userId, messageText, timestamp));
        chatMessageAdapter.notifyItemInserted(chatMessages.size() - 1);
        chatMessagesRecyclerView.scrollToPosition(chatMessages.size() - 1);
        messageInput.setText("");
    }
}
/*
package com.example.youlivealone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private RecyclerView chatMessagesRecyclerView;
    private ChatMessageAdapter chatMessageAdapter;
    private ArrayList<ChatMessage> chatMessages;
    private EditText messageInput;
    private ImageButton sendButton;
    private String userId; // 유저 ID
    private String chatRoomId; // 채팅방 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // 채팅방 ID와 채팅방 이름을 Intent에서 가져오기
        chatRoomId = getIntent().getStringExtra("chatRoomId");
        String chatRoomName = getIntent().getStringExtra("chatRoomName");

        TextView chatRoomTitle = findViewById(R.id.chatRoomTitle);
        chatRoomTitle.setText(chatRoomName);

        // 뷰 초기화
        chatMessagesRecyclerView = findViewById(R.id.chatMessagesRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // SharedPreferences에서 사용자 ID 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        // RecyclerView 설정
        chatMessages = new ArrayList<>();
        chatMessageAdapter = new ChatMessageAdapter(chatMessages, userId);
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatMessagesRecyclerView.setAdapter(chatMessageAdapter);

        // 메시지 전송 버튼 클릭 리스너
        sendButton.setOnClickListener(v -> sendMessage());

        // 서버에서 기존 메시지 로드
        loadChatMessages();
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();

        if (messageText.isEmpty()) {
            Toast.makeText(this, "메시지를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://15.165.92.121:8080/chat/" + chatRoomId + "/send";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", userId);
            jsonBody.put("message", messageText);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    try {
                        String messageId = response.getString("messageId");
                        String timestamp = response.getString("timestamp");

                        ChatMessage newMessage = new ChatMessage(userId, messageText, timestamp);
                        chatMessages.add(newMessage);
                        chatMessageAdapter.notifyItemInserted(chatMessages.size() - 1);
                        chatMessagesRecyclerView.scrollToPosition(chatMessages.size() - 1);

                        messageInput.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(ChatRoomActivity.this, "메시지 전송 실패", Toast.LENGTH_SHORT).show()
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadChatMessages() {
        String url = "http://15.165.92.121:8080/chat/" + chatRoomId + "/messages";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            response -> {
                // 서버로부터 기존 메시지를 로드하여 RecyclerView에 추가
                // 파싱 및 RecyclerView 업데이트 코드 작성
            },
            error -> Toast.makeText(ChatRoomActivity.this, "메시지를 불러오는데 실패했습니다", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

* */