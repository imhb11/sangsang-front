package com.example.youlivealone;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestActivity extends StringRequest {

    final static private String URL = "http://54.79.1.3:8080/login_test.php";
    private Map<String, String> map;

    public LoginRequestActivity(String name, String id, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}