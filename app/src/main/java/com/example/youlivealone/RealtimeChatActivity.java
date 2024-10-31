package com.example.youlivealone;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RealtimeChatActivity extends AppCompatActivity {

    private RecyclerView chatRoomRecyclerView;
    private ChatRoomAdapter chatRoomAdapter;
    private ArrayList<ChatRoom> chatRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_chat);

        chatRoomRecyclerView = findViewById(R.id.chatRoomRecyclerView);
        ImageButton addChatRoomButton = findViewById(R.id.addChatRoomButton);

        chatRoomList = new ArrayList<>();
        chatRoomAdapter = new ChatRoomAdapter(chatRoomList);
        chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecyclerView.setAdapter(chatRoomAdapter);

        addChatRoomButton.setOnClickListener(v -> showAddChatRoomDialog());
    }

    private void showAddChatRoomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_chat_room);

        EditText roomTitleInput = dialog.findViewById(R.id.roomTitleInput);
        EditText roomDescriptionInput = dialog.findViewById(R.id.roomDescriptionInput);
        EditText maxParticipantsInput = dialog.findViewById(R.id.maxParticipantsInput);
        Button confirmButton = dialog.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> {
            String roomTitle = roomTitleInput.getText().toString();
            String roomDescription = roomDescriptionInput.getText().toString();
            String maxParticipants = maxParticipantsInput.getText().toString();

            if (roomTitle.isEmpty() || roomDescription.isEmpty() || maxParticipants.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                //이건 로컬로 채팅방 add가 잘 이루어지는 지 확인을 위한 코드
//                chatRoomList.add(new ChatRoom(roomTitle, roomDescription, Integer.parseInt(maxParticipants), "농구"));
//                chatRoomAdapter.notifyDataSetChanged();
//                dialog.dismiss();


                //카테고리도 가기는 하는 듯
                // POST 요청 보내는 메서드 호출
                createChatRoom(roomTitle, roomDescription, Integer.parseInt(maxParticipants), "농구");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void createChatRoom(String name, String description, int maxParticipants, String category) {
        String url = "http://15.165.92.121:8080/chat/room"; // 서버 주소 설정

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwtToken", null);

        if (token == null) {
            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("description", description);
            jsonBody.put("maxParticipants", maxParticipants);
            jsonBody.put("category", category);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, url,
                    response -> {
                        Toast.makeText(getApplicationContext(), "채팅방 생성 성공", Toast.LENGTH_SHORT).show();
                        chatRoomList.add(new ChatRoom(name, description, maxParticipants, category));
                        chatRoomAdapter.notifyDataSetChanged();
                    },
                    error -> Toast.makeText(getApplicationContext(), "채팅방 생성 실패", Toast.LENGTH_LONG).show()
            ) {
                @Override
                public byte[] getBody() {
                    return jsonBody.toString().getBytes();
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + token); // JWT 토큰을 헤더에 추가
                    return headers;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "JSON 오류 발생", Toast.LENGTH_SHORT).show();
        }
    }
}

