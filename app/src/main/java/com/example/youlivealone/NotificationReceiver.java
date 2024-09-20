//상단바 알림 매일 같은 시간에 설정하기 위한 클래스

package com.example.youlivealone;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "daily_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 알림 채널 생성 (안드로이드 Oreo 이상에서 필요)
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Daily Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // 큰 아이콘 설정
        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(context.getResources(), R.drawable.image1);

        // 알림 빌더 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.image1)
                .setContentTitle("살았음?")
                .setContentText("살았냐고")
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setLargeIcon(mLargeIconForNoti)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // 알림 표시
        notificationManager.notify(1001, builder.build());
    }
}