package com.example.LuxeVista.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.R;
import com.example.LuxeVista.Models.Room;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Room> cartList;
    private OnRemoveClickListener removeClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }

    public CartAdapter(List<Room> cartList, OnRemoveClickListener removeClickListener) {
        this.cartList = cartList;
        this.removeClickListener = removeClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Room room = cartList.get(position);

        holder.textViewTitle.setText(room.getTitle());
        holder.textViewStayDates.setText("Stay: " + room.getDateRange());
        holder.textViewPrice.setText(String.format("$%.2f/night", room.getPrice()));
        holder.imageViewRoom.setImageResource(room.getImageResource());

        holder.buttonRemove.setOnClickListener(v -> {
            if (removeClickListener != null) {
                removeClickListener.onRemoveClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRoom;
        TextView textViewTitle, textViewStayDates, textViewPrice;
        Button buttonRemove;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRoom = itemView.findViewById(R.id.imageViewRoom);
            textViewTitle = itemView.findViewById(R.id.textViewRoomTitle);
            textViewStayDates = itemView.findViewById(R.id.textViewStayDates);
            textViewPrice = itemView.findViewById(R.id.textViewRoomPrice);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}
