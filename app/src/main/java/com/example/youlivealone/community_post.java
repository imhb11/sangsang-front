package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class community_post extends AppCompatActivity {
    private ImageButton likeButton;
    private String postId; // 게시물 ID
    private TextView likesCountView; // 좋아요 수를 표시할 TextView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_post);


        TextView titleView = findViewById(R.id.title);
        TextView contentView = findViewById(R.id.post_content);
        TextView userIdView = findViewById(R.id.userid);

        Intent intent = getIntent();
        Post selectedPost = (Post) intent.getSerializableExtra("selectedPost");
//        postId = selectedPost.getId(); // 게시물 ID 가져오기

        // 데이터 설정
        titleView.setText(selectedPost.getTitle());
        userIdView.setText(String.valueOf(selectedPost.getUserId())); // 사용자 ID 출력
        contentView.setText(selectedPost.getContent()); // 내용 출력

        likeButton = findViewById(R.id.goodbutton); // XML에서 버튼 ID에 맞춰 가져오기

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLikeRequest();
            }
        });

        fetchLikesCount(); // 좋아요 수 가져오기

    }

    private void fetchLikesCount() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://15.165.92.121:8080/posts/{id}/like-count"; // 서버 URL 설정

        // GET 요청 생성
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int likesCount = response.getInt("likesCount"); // 응답에서 좋아요 수 가져오기
                            likesCountView.setText("좋아요 수: " + likesCount); // TextView에 좋아요 수 표시
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 발생 시 처리
                        System.out.println("Error: " + error.toString());
                    }
        });

        // 요청을 큐에 추가
        queue.add(jsonObjectRequest);
    }

    private void sendLikeRequest() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwt_token", null); // 저장된 토큰 가져오기

        if (jwtToken == null) {
            // 토큰이 없을 경우 처리
            System.out.println("JWT 토큰이 없습니다.");
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://15.165.92.121:8080/posts/"; // 서버 URL 설정

        // JSON 객체 생성
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("postId", postId);
            jsonBody.put("jwt", jwtToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // POST 요청 생성
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 성공적으로 응답을 받았을 때 처리
                        System.out.println("Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 발생 시 처리
                        System.out.println("Error: " + error.toString());
                    }
                });

        // 요청을 큐에 추가
        queue.add(jsonObjectRequest);

    }
}
