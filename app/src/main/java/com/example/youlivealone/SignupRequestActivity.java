package com.example.youlivealone;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequestActivity extends StringRequest {

    final static private String URL = "http://54.79.1.3:8080/member/register";
    private Map<String, String> map;

    public SignupRequestActivity(String name, String id, String birthDate, String pw, String email, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
        map.put("birthDate", birthDate);
        map.put("pw", pw);
        map.put("email", email);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
