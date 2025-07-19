package com.example.LuxeVista.Adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Data_Base.DBHelper;
import com.example.LuxeVista.R;
import com.example.LuxeVista.Models.Room;
import com.example.LuxeVista.Models.SessionManager;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private Context context;
    private DBHelper dbHelper;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    public RoomAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
        this.dbHelper = new DBHelper(context);
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
        holder.textViewRoomPrice.setText("$" + room.getPrice());
        holder.imageViewRoom.setImageResource(room.getImageResource());


        Calendar checkInCalendar = Calendar.getInstance();
        Calendar checkOutCalendar = Calendar.getInstance();
        checkOutCalendar.add(Calendar.DAY_OF_MONTH, 1);


        holder.cardCheckIn.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    (view, year, month, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);


                        if (selectedDate.before(Calendar.getInstance())) {
                            Toast.makeText(context, "Cannot select past dates", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        checkInCalendar.set(year, month, dayOfMonth);
                        holder.textViewCheckInDate.setText(dateFormat.format(checkInCalendar.getTime()));


                        if (checkOutCalendar.before(checkInCalendar) ||
                                isSameDay(checkInCalendar, checkOutCalendar)) {
                            checkOutCalendar.setTime(checkInCalendar.getTime());
                            checkOutCalendar.add(Calendar.DAY_OF_MONTH, 1);
                            holder.textViewCheckOutDate.setText(dateFormat.format(checkOutCalendar.getTime()));
                        }

                        updateStayDuration(holder, checkInCalendar, checkOutCalendar, room.getPrice());
                    },
                    checkInCalendar.get(Calendar.YEAR),
                    checkInCalendar.get(Calendar.MONTH),
                    checkInCalendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });


        holder.cardCheckOut.setOnClickListener(v -> {

            if (holder.textViewCheckInDate.getText().toString().equals("Select date")) {
                Toast.makeText(context, "Please select check-in date first", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar minDate = Calendar.getInstance();
            minDate.setTime(checkInCalendar.getTime());
            minDate.add(Calendar.DAY_OF_MONTH, 1);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    (view, year, month, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);


                        if (selectedDate.before(checkInCalendar) ||
                                isSameDay(selectedDate, checkInCalendar)) {
                            Toast.makeText(context, "Check-out must be after check-in", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        checkOutCalendar.set(year, month, dayOfMonth);
                        holder.textViewCheckOutDate.setText(dateFormat.format(checkOutCalendar.getTime()));

                        updateStayDuration(holder, checkInCalendar, checkOutCalendar, room.getPrice());
                    },
                    checkOutCalendar.get(Calendar.YEAR),
                    checkOutCalendar.get(Calendar.MONTH),
                    checkOutCalendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.getDatePicker().setMinDate(checkInCalendar.getTimeInMillis() + TimeUnit.DAYS.toMillis(1));
            datePickerDialog.show();
        });


        holder.textViewCheckInDate.setText("Select date");
        holder.textViewCheckOutDate.setText("Select date");
        holder.textViewStayDuration.setText("Select dates to see stay duration");
        holder.textViewTotalNights.setText("");


        holder.buttonBookNow.setOnClickListener(v -> {
            if (SessionManager.isLoggedIn(context)) {

                if (holder.textViewCheckInDate.getText().toString().equals("Select date") ||
                        holder.textViewCheckOutDate.getText().toString().equals("Select date")) {
                    Toast.makeText(context, "Please select check-in and check-out dates", Toast.LENGTH_SHORT).show();
                    return;
                }

                int userId = SessionManager.getUserId(context);

                if (!dbHelper.isRentedRoomsTableExists()) {
                    Toast.makeText(context, "Database error: table doesn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }


                long nights = calculateNights(checkInCalendar, checkOutCalendar);

                boolean success = dbHelper.addToRentedRooms(
                        userId,
                        room.getName(),
                        room.getPrice(),
                        dateFormat.format(checkInCalendar.getTime()),
                        dateFormat.format(checkOutCalendar.getTime()),
                        (int) nights);

                if (success) {
                    Toast.makeText(context, room.getName() + " added to your bookings", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to add booking - please try again", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please login to book a room", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }


    private long calculateNights(Calendar checkIn, Calendar checkOut) {
        long diffInMillis = checkOut.getTimeInMillis() - checkIn.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(diffInMillis);
    }


    private void updateStayDuration(RoomViewHolder holder, Calendar checkIn, Calendar checkOut, double price) {
        long nights = calculateNights(checkIn, checkOut);

        if (nights <= 0) {
            holder.textViewStayDuration.setText("Invalid date selection");
            holder.textViewTotalNights.setText("");
            return;
        }

        holder.textViewStayDuration.setText(nights + " night" + (nights > 1 ? "s" : "") + " stay");
        holder.textViewTotalNights.setText("× " + nights);


    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRoom;
        TextView textViewRoomName, textViewRoomDescription, textViewRoomPrice;
        TextView textViewCheckInDate, textViewCheckOutDate, textViewStayDuration, textViewTotalNights;
        MaterialCardView cardCheckIn, cardCheckOut;
        Button buttonBookNow;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewRoom = itemView.findViewById(R.id.imageViewRoom);
            textViewRoomName = itemView.findViewById(R.id.textViewRoomName);
            textViewRoomDescription = itemView.findViewById(R.id.textViewRoomDescription);
            textViewRoomPrice = itemView.findViewById(R.id.textViewRoomPrice);
            textViewCheckInDate = itemView.findViewById(R.id.textViewCheckInDate);
            textViewCheckOutDate = itemView.findViewById(R.id.textViewCheckOutDate);
            textViewStayDuration = itemView.findViewById(R.id.textViewStayDuration);
            textViewTotalNights = itemView.findViewById(R.id.textViewTotalNights);
            cardCheckIn = itemView.findViewById(R.id.cardCheckIn);
            cardCheckOut = itemView.findViewById(R.id.cardCheckOut);
            buttonBookNow = itemView.findViewById(R.id.buttonBookNow);
        }
    }
}