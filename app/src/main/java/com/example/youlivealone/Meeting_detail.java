package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Meeting_detail extends AppCompatActivity implements OnMapReadyCallback{
    private MapView mapView;
    private NaverMap mnavermap;

    private String postId; // 게시물 ID
    private TextView titleTextView; // 제목 TextView
    private TextView introTextView; // 한줄소개 TextView
    private TextView participantsTextView; // 참여인원 TextView
    private TextView contentTextView; // 본문 내용 TextView
    private ImageButton applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_detail);
        // UI 컴포넌트 초기화
        titleTextView = findViewById(R.id.meeting_title);
        introTextView = findViewById(R.id.meeting_summary);
        participantsTextView = findViewById(R.id.personnel);
        contentTextView = findViewById(R.id.meeting_text);
        applyButton = findViewById(R.id.meeting_apply);

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_detail.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_detail.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_detail.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_detail.this, Mypage.class);
            startActivity(intent);
        });

        findViewById(R.id.detail_back).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting_detail.this, Meeting_category.class);
            startActivity(intent);
        });

        //지도화면
        mapView = findViewById(R.id.meeting_detail_map_fragment);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //화면에 데이터 출력
        // Intent에서 데이터를 가져옴
        String postId = getIntent().getStringExtra("postId");
        String title = getIntent().getStringExtra("title");
        String introduction = getIntent().getStringExtra("introduction");
        String content = getIntent().getStringExtra("content");
        int memberCount = getIntent().getIntExtra("memberCount", 0);

        titleTextView.setText(title);
        introTextView.setText(introduction);
        contentTextView.setText(content);
        participantsTextView.setText(String.valueOf(memberCount));

        //소모임 신청 버튼
        applyButton.setOnClickListener(v -> {
            sendApplicationRequest(postId); // postId를 인자로 전달
        });

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.mnavermap = naverMap;

        // 서버에서 좌표 데이터를 불러와 지도에 마커 추가
        loadMarkerDataFromServer("http://your-server-url.com/coordinates"); // 서버 URL에 맞게 수정
    }
    // 서버에서 위도와 경도 데이터를 받아와 마커를 추가하는 메서드
    private void loadMarkerDataFromServer(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject location = response.getJSONObject(i);
                                double latitude = location.getDouble("latitude");
                                double longitude = location.getDouble("longitude");

                                // 위도와 경도에 마커 추가
                                addMarker(latitude, longitude);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Meeting_detail.this, "Failed to load marker data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }

    // 마커를 지도에 추가하는 메서드
    private void addMarker(double latitude, double longitude) {
        Marker marker = new Marker();
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(mnavermap);
    }




    //소모임 신청
    private void sendApplicationRequest(String postId) {
        String url = "http://15.165.92.121:8080/meetings/{meetingId}/join"; // 서버 URL 수정
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject applicationData = new JSONObject();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId", null); // 기본값으로 null 설정

            applicationData.put("userId", userId); // 사용자 ID
            applicationData.put("postId", postId); // 모임 ID
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                applicationData,
                response -> {
                    // 서버에서 성공 응답을 받은 경우
                    Toast.makeText(this, "신청이 되었습니다.", Toast.LENGTH_SHORT).show();
                    // 필요에 따라 UI 업데이트 (예: 버튼 상태 변경)
                },
                error -> {
                    // 서버 요청 실패 처리
                    Toast.makeText(this, "신청 실패", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonObjectRequest);
    }

}
