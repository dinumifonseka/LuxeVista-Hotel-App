package com.example.LuxeVista.Activity_Pages;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LuxeVista.Data_Base.DBHelper;
import com.example.LuxeVista.Models.SessionManager;
import com.example.LuxeVista.R;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEmail,
            editTextPhone, editTextNic, editTextPassword;
    private Button buttonUpdate;
    private DBHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        dbHelper = new DBHelper(this);
        sessionManager = new SessionManager(this);

        editTextFirstName = findViewById(R.id.editTextUpdateFirstName);
        editTextLastName = findViewById(R.id.editTextUpdateLastName);
        editTextEmail = findViewById(R.id.editTextUpdateEmail);
        editTextPhone = findViewById(R.id.editTextUpdatePhone);
        editTextNic = findViewById(R.id.editTextUpdateNic);
        editTextPassword = findViewById(R.id.editTextUpdatePassword);
        buttonUpdate = findViewById(R.id.buttonUpdate);


        editTextFirstName.setText(sessionManager.getFirstName());
        editTextLastName.setText(sessionManager.getLastName());
        editTextEmail.setText(sessionManager.getEmail());
        editTextPhone.setText(sessionManager.getPhone());
        editTextNic.setText(sessionManager.getNic());

        buttonUpdate.setOnClickListener(v -> updateProfile());
    }

    private void updateProfile() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String nic = editTextNic.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || nic.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean success = dbHelper.updateUser(
                sessionManager.getUserId(),
                firstName,
                lastName,
                email,
                phone,
                nic,
                password.isEmpty() ? null : password
        );

        if (success) {

            sessionManager.saveLoginSession(
                    sessionManager.getUserId(),
                    firstName,
                    lastName,
                    email,
                    phone,
                    nic
            );
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}