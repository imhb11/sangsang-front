package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource; //위치 반환 구현체
    private NaverMap mnavermap;
    private MapView mapView;
    private RequestQueue requestQueue;
    private ArrayList<Marker> markers = new ArrayList<>(); // 마커 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        //지도 불러오기
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("m5c61qtjs2")
        );

        //위치 표시
        mapView = findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);



        // 버튼 작동코드들

        findViewById(R.id.back).setOnClickListener(v -> {
            Intent intent = new Intent(Map.this, Meeting_detail.class);
            startActivity(intent);
        });

        findViewById(R.id.btnchinmok).setOnClickListener(v -> displayMarkersForCategory("category1"));
        findViewById(R.id.btnexercise).setOnClickListener(v -> displayMarkersForCategory("category2"));
        findViewById(R.id.btnmovie).setOnClickListener(v -> displayMarkersForCategory("category3"));
        findViewById(R.id.btnfood).setOnClickListener(v -> displayMarkersForCategory("category4"));
        findViewById(R.id.btndongchang).setOnClickListener(v -> displayMarkersForCategory("category5"));
        findViewById(R.id.btnstudy).setOnClickListener(v -> displayMarkersForCategory("category6"));
    }

    //권한 여부처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,grantResults )){
            if (!locationSource.isActivated()){
                mnavermap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        //위치 표시
        this.mnavermap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }

    // 카테고리 선택 시 해당 카테고리에 맞는 마커 표시
    private void displayMarkersForCategory(String category) {
        // 기존 마커 삭제
        for (Marker marker : markers) {
            marker.setMap(null);
        }
        markers.clear();

        // 서버에서 선택된 카테고리에 맞는 위치 데이터를 가져옴
        String url = "http://서버주소/locations?category=" + category;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray locations = response.getJSONArray("locations");
                        for (int i = 0; i < locations.length(); i++) {
                            JSONObject location = locations.getJSONObject(i);
                            double latitude = location.getDouble("latitude");
                            double longitude = location.getDouble("longitude");

                            // 마커 생성 및 위치 설정
                            Marker marker = new Marker();
                            marker.setPosition(new LatLng(latitude, longitude));
                            marker.setMap(mnavermap);
                            markers.add(marker);
                        }
                    } catch (Exception e) {
                        Log.e("MapActivity", "Error parsing JSON", e);
                    }
                },
                error -> Log.e("MapActivity", "Error fetching location data", error)
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}