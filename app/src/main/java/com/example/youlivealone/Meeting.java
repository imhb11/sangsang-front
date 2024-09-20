package com.example.youlivealone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.view.View;
import android.widget.ImageButton;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.List;

public class Meeting extends AppCompatActivity implements OnMapReadyCallback {
    private GridLayout meeting_categorygrid;
    private RoomViewModel roomViewModel;
    private MapView mapView;
    private NaverMap mnavermap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource; //위치 반환 구현체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting);

        //지도 불러오기
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("m5c61qtjs2")
        );

        //위치 표시
        mapView = findViewById(R.id.meeting_map_fragment);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);



        //하단바 버튼 작동코드들

        findViewById(R.id.map).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Map.class);
            startActivity(intent);
        });

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(Meeting.this, Mypage.class);
            startActivity(intent);
        });

        //카테고리 버튼 클릭 시
        GridLayout gatheringCategoryLayout = findViewById(R.id.gathering_category);
        for (int i = 0; i < gatheringCategoryLayout.getChildCount(); i++) {
            View child = gatheringCategoryLayout.getChildAt(i);
            if (child instanceof ImageButton) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Meeting.this, Meeting_category.class);
                        // 각 버튼에 맞는 데이터를 인텐트에 추가
                        intent.putExtra("buttonId", v.getId());
                        startActivity(intent);
                    }
                });
            }
        }
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
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);;

    }



}
