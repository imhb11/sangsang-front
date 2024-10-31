package com.example.youlivealone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    //백엔드에서 해당 리스트에 전체 채팅방 객체에 대해서 list로 줘야한다
    private ArrayList<ChatRoom> chatRooms;

    public ChatRoomAdapter(ArrayList<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_room, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
//        ExtendedChatRoom chatRoom = (ExtendedChatRoom) chatRooms.get(position);
//        holder.chatRoomTitle.setText(chatRoom.getName());
//        holder.participantCount.setText("참여자 수: " + chatRoom.getCurrentParticipants() + "/" + chatRoom.getMaxParticipants());
//        holder.lastMessageTime.setText(chatRoom.getLastMessageTime());
        ChatRoom chatRoom = chatRooms.get(position);
        holder.chatRoomTitle.setText(chatRoom.getName());
        holder.participantCount.setText("참여자 수: " + chatRoom.getParticipantCount());
        holder.lastMessageTime.setText(chatRoom.getLastMessageTime()); // 마지막 메시지 시간 표시

    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    public static class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView chatRoomTitle, participantCount, lastMessageTime;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            chatRoomTitle = itemView.findViewById(R.id.chatroom_name);
            participantCount = itemView.findViewById(R.id.chatroom_participants);
            lastMessageTime = itemView.findViewById(R.id.chatroom_last_message_time);
        }
    }
}
