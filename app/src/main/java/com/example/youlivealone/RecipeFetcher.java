package com.example.youlivealone;

import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RecipeFetcher {

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";


    public void fetchRecipe(List<String> ingredients, String apiKey, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
                .build();

        String prompt = "Using ingredients: " + String.join(", ", ingredients) + ", suggest a recipe.";
        String json = "{"
                + "\"model\": \"gpt-4-0613\","
                + "\"prompt\": \"" + prompt + "\","
                + "\"max_tokens\": 150"
                + "}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(GPT_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        client.newCall(request).enqueue(callback);
    }

}
