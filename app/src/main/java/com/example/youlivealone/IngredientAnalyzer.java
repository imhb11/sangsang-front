package com.example.youlivealone;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import androidx.annotation.OptIn;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import org.tensorflow.lite.Interpreter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class IngredientAnalyzer implements ImageAnalysis.Analyzer {

    private final Interpreter interpreter;
    private final List<String> labels;
    private static final int INPUT_SIZE = 416;  // 모델이 기대하는 입력 크기

    public IngredientAnalyzer(Interpreter interpreter, List<String> labels) {
        this.interpreter = interpreter;
        this.labels = labels;
    }

    @Override
    public void analyze(ImageProxy imageProxy) {
        ByteBuffer tfInput = processImageToTensor(imageProxy);
        float[][] output = new float[1][labels.size()];

        interpreter.run(tfInput, output);
        int maxIndex = getMaxIndex(output[0]);
        String recognizedIngredient = labels.get(maxIndex);

        imageProxy.close();
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private ByteBuffer processImageToTensor(ImageProxy imageProxy) {
        Image image = imageProxy.getImage();
        if (image == null) {
            return ByteBuffer.allocateDirect(0);
        }

        Bitmap bitmap = imageToBitmap(image);
        return convertBitmapToByteBuffer(bitmap);
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        // RGB 데이터에 맞춘 정확한 크기의 ByteBuffer 할당
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(INPUT_SIZE * INPUT_SIZE * 3 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        // 입력 크기에 맞게 비트맵 크기 조정
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        int[] intValues = new int[INPUT_SIZE * INPUT_SIZE];
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());

        // 각 픽셀 RGB 값을 0~1 범위로 정규화하여 ByteBuffer에 추가
        for (int pixel : intValues) {
            byteBuffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f); // Red
            byteBuffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);  // Green
            byteBuffer.putFloat((pixel & 0xFF) / 255.0f);         // Blue
        }

        return byteBuffer;
    }

    private int getMaxIndex(float[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private Bitmap imageToBitmap(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4); // ARGB_8888 형식
        buffer.order(ByteOrder.nativeOrder());

        Image.Plane[] planes = image.getPlanes();
        ByteBuffer rgbBuffer = planes[0].getBuffer();

        buffer.put(rgbBuffer);
        buffer.rewind();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);

        return bitmap;
    }
}
