package com.example.youlivealone;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecipeService {

    private static final String API_KEY = "sk-iY801Gi6Ch8E9ufcYzj8GLJSilRa-r_B-buktFOaP5T3BlbkFJlojeq1IPZoR90Gq79djz974JEbVN8_Ws4Z3ij7qk4A";
    private static final String API_URL = "https://api.openai.com/v1/completions";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void getRecipeSuggestions(List<String> ingredients) {
        OkHttpClient client = new OkHttpClient();

        String ingredientString = String.join(", ", ingredients);
        String prompt = "냉장고 속 식재료 : " + ingredientString + ". 를 활용한 레시피 추천해.";

        JsonObject json = new JsonObject();
        json.addProperty("model", "text-davinci-003");
        json.addProperty("prompt", prompt);
        json.addProperty("max_tokens", 100);

        RequestBody body = RequestBody.create(JSON, json.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("RecipeService", "API call failed: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JsonObject jsonResponse = JsonParser.parseString(responseData).getAsJsonObject();
                    String recipe = jsonResponse.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
                    Log.d("RecipeService", "Recipe: " + recipe);
                    // UI에 레시피 표시하는 로직 추가
                } else {
                    Log.e("RecipeService", "API call unsuccessful: " + response.message());
                }
            }
        });
    }
}