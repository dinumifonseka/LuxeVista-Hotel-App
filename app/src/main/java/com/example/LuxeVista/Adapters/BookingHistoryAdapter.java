package com.example.LuxeVista.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Models.BookingHistoryItem;
import com.example.LuxeVista.R;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder> {

    private List<BookingHistoryItem> bookingList;

    public BookingHistoryAdapter(List<BookingHistoryItem> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingHistoryItem item = bookingList.get(position);

        holder.hotelName.setText(item.getHotelName());
        holder.roomType.setText(item.getRoomType());
        holder.bookingDate.setText(item.getBookingDate());
        holder.bookingStatus.setText(item.getBookingStatus());
        holder.totalPrice.setText(item.getTotalPrice());
        holder.hotelImage.setImageResource(item.getHotelImage());

        // Set status background based on status
        switch (item.getBookingStatus().toLowerCase()) {
            case "completed":
                holder.statusLayout.setBackgroundResource(R.drawable.status_background_completed);
                break;
            case "upcoming":
                holder.statusLayout.setBackgroundResource(R.drawable.status_background_upcoming);
                break;
            case "cancelled":
                holder.statusLayout.setBackgroundResource(R.drawable.status_background_cancelled);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, roomType, bookingDate, bookingStatus, totalPrice;
        ImageView hotelImage;
        View statusLayout;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.textViewHotelName);
            roomType = itemView.findViewById(R.id.textViewRoomType);
            bookingDate = itemView.findViewById(R.id.textViewBookingDate);
            bookingStatus = itemView.findViewById(R.id.textViewBookingStatus);
            totalPrice = itemView.findViewById(R.id.textViewTotalPrice);
            hotelImage = itemView.findViewById(R.id.imageViewHotel);
            statusLayout = itemView.findViewById(R.id.layoutStatus);
        }
    }
}