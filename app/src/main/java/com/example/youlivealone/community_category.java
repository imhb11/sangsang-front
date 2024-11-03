package com.example.youlivealone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class community_category extends AppCompatActivity {

    private ListView hotlist;
    private ListView postlist;

    private ArrayAdapter<Post> hotAdapter;
    private ArrayAdapter<Post> postAdapter;

    private List<Post> hotPosts = new ArrayList<>();
    private List<Post> postItems = new ArrayList<>();


    private static final String HOT_POSTS_URL = "http://15.165.92.121:8080/categories/{categoryId}/posts";
    private static final String POSTS_URL = "http://15.165.92.121:8080/categories/{categoryId}/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        // 핫리스트와 포스트 리스트 초기화
        hotlist = findViewById(R.id.hotpost);
        postlist = findViewById(R.id.post_content);

        // 어댑터 생성
        hotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hotPosts);
        postAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postItems);

        // 어댑터 설정
        hotlist.setAdapter(hotAdapter);
        postlist.setAdapter(postAdapter);

        // SharedPreferences에서 카테고리 ID 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int categoryId = sharedPreferences.getInt("categoryId", -1); // 기본값 -1

        if (categoryId != -1) {
            // URL에 카테고리 ID 적용
            String hotPostsUrl = HOT_POSTS_URL.replace("{categoryId}", String.valueOf(categoryId));
            String postsUrl = POSTS_URL.replace("{categoryId}", String.valueOf(categoryId));

            // API 호출하여 데이터 가져오기
            loadHotPosts(hotPostsUrl);
            loadPostList(postsUrl);
        } else {
            Toast.makeText(this, "카테고리 ID를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
            // 필요 시 액티비티 종료 또는 기본 화면 처리
        }



        // 리스트 항목 클릭 리스너 설정
        postlist.setOnItemClickListener((parent, view, position, id) -> {
            // 선택된 항목의 데이터 가져오기
            Post selectedPost = postItems.get(position);

            // Intent 생성하여 새로운 액티비티로 이동
            Intent intent = new Intent(community_category.this, community_post.class);
            intent.putExtra("selectedPost", selectedPost);
            startActivity(intent);
        });

        hotlist.setOnItemClickListener((parent, view, position, id) -> {
            Post selectedHotPost = hotPosts.get(position);

            Intent intent = new Intent(community_category.this, community_post.class);
            intent.putExtra("selectedPost", selectedHotPost);
            startActivity(intent);
        });



        /*
        //<인기게시글>
        // ListView를 찾기
        ListView hotlist = findViewById(R.id.hotpost);
        // 데이터 소스 생성
        String[] hotdata = {"항목 1", "항목 2", "항목 3", "항목 4", "항목 5"};
        // ArrayAdapter 생성
        ArrayAdapter<String> hotadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hotdata);
        // ListView에 어댑터 설정
        hotlist.setAdapter(hotadapter);
        hotlist.setOnItemClickListener((parent, view, position, id) -> {
            // 선택된 항목의 데이터 가져오기
            String selectedItem = hotdata[position];

            // Intent 생성
            Intent intent = new Intent(community_category.this, community_post.class);//게시판 선택 후 클래스
            // 선택된 항목의 데이터를 전달
            intent.putExtra("selectedItem", selectedItem);

            // Activity 전환
            startActivity(intent);
        });

        //톡방 선택
        LinearLayout talksLayout = findViewById(R.id.talks);

        // LinearLayout 안에 있는 각 ImageButton 가져와서 클릭 리스너 설정
        for (int i = 0; i < talksLayout.getChildCount(); i++) {
            View child = talksLayout.getChildAt(i);
            if (child instanceof ImageButton) {
                ImageButton button = (ImageButton) child;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(community_category.this, MessageBoard.class);
                        startActivity(intent);
                    }
                });
            }
        }

        //<게시글>

        ListView postlist = findViewById(R.id.post);
        String[] postdata = {"항목 1", "항목 2", "항목 3", "항목 4", "항목 5"};
        ArrayAdapter<String> postadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postdata);
        postlist.setAdapter(postadapter);
        // 항목 클릭 리스너 설정
        postlist.setOnItemClickListener((parent, view, position, id) -> {
            // 선택된 항목의 데이터 가져오기
            String selectedItem = postdata[position];

            // Intent 생성
            Intent intent = new Intent(community_category.this, community_post.class);//게시판 선택 후 클래스
            // 선택된 항목의 데이터를 전달
            intent.putExtra("selectedItem", selectedItem);

            // Activity 전환
            startActivity(intent);
        });
        */


        //게시판 버튼 클릭시
        findViewById(R.id.text_post).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, MessageBoard.class);
            startActivity(intent);
        });

        //버튼 작동코드들

        findViewById(R.id.check).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Check.class);
            startActivity(intent);
        });

        findViewById(R.id.home).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Chat.class);
            startActivity(intent);
        });
        findViewById(R.id.mypage).setOnClickListener(v -> {
            Intent intent = new Intent(community_category.this, Mypage.class);
            startActivity(intent);
        });

    }

    private void loadHotPosts(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject post = response.getJSONObject(i);
                                String postId = post.getString("id"); // postId 가져오기
                                int id = post.getInt("categoryId");
                                String title = post.getString("title");
                                String content = post.getString("content");
                                hotPosts.add(new Post(postId, id, title, content));
                            }

                            hotAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(community_category.this, "Error loading hot posts", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }

    private void loadPostList(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject post = response.getJSONObject(i);
                                String postId = post.getString("id"); // postId 가져오기
                                int id = post.getInt("categoryId");
                                String title = post.getString("title");
                                String content = post.getString("content");
                                postItems.add(new Post(postId, id, title, content));
                            }

                            postAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(community_category.this, "Error loading posts", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }




}
