package com.example.LuxeVista.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Models.Room;
import com.example.LuxeVista.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

public class SearchRoomAdapter extends RecyclerView.Adapter<SearchRoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private Context context;

    public SearchRoomAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);

        holder.textViewRoomName.setText(room.getName());
        holder.textViewRoomDescription.setText(room.getDescription());
        holder.textViewPrice.setText(String.format(Locale.US, "$%.0f/night", room.getPrice()));
        holder.imageViewRoom.setImageResource(room.getImageResource());


        holder.buttonBookNow.setOnClickListener(v -> {
            Toast.makeText(context, "Booking " + room.getName(), Toast.LENGTH_SHORT).show();

        });

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Selected: " + room.getName(), Toast.LENGTH_SHORT).show();
            // Add room details navigation here
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    // Method to update the list for search filtering
    public void updateList(List<Room> newList) {
        this.roomList = newList;
        notifyDataSetChanged();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewRoom;
        ImageView imageRoom;
        TextView textViewRoomName;
        TextView textViewRoomDescription;
        TextView textViewPrice;
        MaterialButton buttonBookNow;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRoom = itemView.findViewById(R.id.imageViewRoom);
            textViewRoomName = itemView.findViewById(R.id.textViewRoomName);
            textViewRoomDescription = itemView.findViewById(R.id.textViewRoomDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            buttonBookNow = itemView.findViewById(R.id.buttonBookNow);
        }
    }
}