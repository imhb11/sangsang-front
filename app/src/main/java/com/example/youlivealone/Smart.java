package com.example.youlivealone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;
import org.tensorflow.lite.Interpreter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Smart extends AppCompatActivity {

    private Interpreter interpreter;
    private IngredientAnalyzer ingredientAnalyzer;
    private RecipeFetcher recipeFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart);

        // 카메라 권한 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            initializeComponents();
        }
    }

    private void initializeComponents() {
        try {
            // TensorFlow Lite 모델과 라벨 초기화
            MappedByteBuffer tfliteModel = loadModelFile("ingredient_model.tflite");
            List<String> labels = Arrays.asList("Tomato", "Onion", "Garlic"); // 예시 라벨
            interpreter = new Interpreter(tfliteModel);
            ingredientAnalyzer = new IngredientAnalyzer(interpreter, labels);
            recipeFetcher = new RecipeFetcher();

            // 카메라 설정
            startCamera();

            // 식재료 인식 후 레시피 요청
            onIngredientsRecognized(Arrays.asList("Tomato", "Onion"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // 카메라 바인딩을 해제하고 다시 바인딩
                cameraProvider.unbindAll();

                Preview preview = new Preview.Builder().build();
                ImageCapture imageCapture = new ImageCapture.Builder().build();
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), ingredientAnalyzer);

                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture, imageAnalysis);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void onIngredientsRecognized(List<String> ingredients) {
        recipeFetcher.fetchRecipe(ingredients, "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", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> ((TextView) findViewById(R.id.recipeTextView)).setText("Error: " + e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String recipe = parseGPTResponse(response.body().string());
                    runOnUiThread(() -> ((TextView) findViewById(R.id.recipeTextView)).setText(recipe));
                }
            }
        });
    }

    // 모델 파일을 assets에서 MappedByteBuffer 형식으로 불러오는 메서드
    private MappedByteBuffer loadModelFile(String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private String parseGPTResponse(String response) {
        // GPT 응답에서 텍스트 추출
        return "Recipe result here"; // 임시 예시
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeComponents();
        }
    }
}
