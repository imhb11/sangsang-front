package com.example.youlivealone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SELF = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private ArrayList<ChatMessage> chatMessages;
    private String userId;
    private Context context;

    public ChatMessageAdapter(ArrayList<ChatMessage> chatMessages, String userId) {
        this.chatMessages = chatMessages;
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if (chatMessage.getSenderId().equals(userId)) {
            Log.d("ChatMessageAdapter", "Self message detected for position: " + position);
            return VIEW_TYPE_SELF;
        } else {
            Log.d("ChatMessageAdapter", "Other message detected for position: " + position+"chatMessage.getSenderId()"+ chatMessage.getSenderId()+"//"+userId);
            return VIEW_TYPE_OTHER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SELF) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_self, parent, false);
            return new SelfMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_other, parent, false);
            return new OtherMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if (holder instanceof SelfMessageViewHolder) {
            ((SelfMessageViewHolder) holder).bind(chatMessage);
        } else if (holder instanceof OtherMessageViewHolder) {
            ((OtherMessageViewHolder) holder).bind(chatMessage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    // ViewHolder for self messages
    public static class SelfMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageTimestamp;

        public SelfMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageTimestamp = itemView.findViewById(R.id.messageTimestamp);
        }

        public void bind(ChatMessage chatMessage) {
            messageText.setText(chatMessage.getContent());
            messageTimestamp.setText(chatMessage.getTimestamp());
        }
    }

    // ViewHolder for other messages
    public static class OtherMessageViewHolder extends RecyclerView.ViewHolder {
        TextView userId, messageText, messageTimestamp;

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            messageText = itemView.findViewById(R.id.messageText);
            messageTimestamp = itemView.findViewById(R.id.messageTimestamp);
        }

        public void bind(ChatMessage chatMessage) {
            userId.setText(chatMessage.getSenderId());
            messageText.setText(chatMessage.getContent());
            messageTimestamp.setText(chatMessage.getTimestamp());
        }
    }
}
