package com.example.youlivealone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationSettingsActivity extends AppCompatActivity {

    // UI Components
    private Switch switchGlobalNotifications;
    private Switch switchCommentNotifications;
    private Switch switchChatNotifications;
    private Switch switchLikeNotifications;

    // SharedPreferences key
    private static final String PREFS_NAME = "NotificationSettings";
    private static final String KEY_GLOBAL_NOTIFICATIONS = "global_notifications";
    private static final String KEY_COMMENT_NOTIFICATIONS = "comment_notifications";
    private static final String KEY_CHAT_NOTIFICATIONS = "chat_notifications";
    private static final String KEY_LIKE_NOTIFICATIONS = "like_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        // Find views by ID
        switchGlobalNotifications = findViewById(R.id.switch_global_notifications);
        switchCommentNotifications = findViewById(R.id.switch_comment_notifications);
        switchChatNotifications = findViewById(R.id.switch_chat_notifications);
        switchLikeNotifications = findViewById(R.id.switch_like_notifications);

        // Load saved preferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean globalNotifications = preferences.getBoolean(KEY_GLOBAL_NOTIFICATIONS, true);
        boolean commentNotifications = preferences.getBoolean(KEY_COMMENT_NOTIFICATIONS, true);
        boolean chatNotifications = preferences.getBoolean(KEY_CHAT_NOTIFICATIONS, true);
        boolean likeNotifications = preferences.getBoolean(KEY_LIKE_NOTIFICATIONS, true);

        // Set initial switch states
        switchGlobalNotifications.setChecked(globalNotifications);
        switchCommentNotifications.setChecked(commentNotifications);
        switchChatNotifications.setChecked(chatNotifications);
        switchLikeNotifications.setChecked(likeNotifications);

        // Listener for Global Notifications switch
        switchGlobalNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(KEY_GLOBAL_NOTIFICATIONS, isChecked);
                // Logic for enabling/disabling all notifications
                if (!isChecked) {
                    switchCommentNotifications.setChecked(false);
                    switchChatNotifications.setChecked(false);
                    switchLikeNotifications.setChecked(false);
                    savePreference(KEY_COMMENT_NOTIFICATIONS, false);
                    savePreference(KEY_CHAT_NOTIFICATIONS, false);
                    savePreference(KEY_LIKE_NOTIFICATIONS, false);
                }
            }
        });

        // Listener for Comment Notifications switch
        switchCommentNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(KEY_COMMENT_NOTIFICATIONS, isChecked);
            }
        });

        // Listener for Chat Room Notifications switch
        switchChatNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(KEY_CHAT_NOTIFICATIONS, isChecked);
            }
        });

        // Listener for Like Notifications switch
        switchLikeNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                savePreference(KEY_LIKE_NOTIFICATIONS, isChecked);
            }
        });
    }

    // Method to save preference
    private void savePreference(String key, boolean value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
