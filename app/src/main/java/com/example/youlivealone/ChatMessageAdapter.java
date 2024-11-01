// ChatMessageAdapter.java
package com.example.youlivealone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder> {

    private ArrayList<ChatMessage> chatMessages;

    public ChatMessageAdapter(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        holder.userIdTextView.setText(chatMessage.getUserId());
        holder.messageTextView.setText(chatMessage.getMessage());
        holder.timestampTextView.setText(chatMessage.getTimestamp());
    }

    @Override
    public int getItemCount() { return chatMessages.size(); }

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {
        TextView userIdTextView, messageTextView, timestampTextView;

        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
