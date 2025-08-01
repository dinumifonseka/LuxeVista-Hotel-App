package com.example.LuxeVista.Models;

public class UserModel {
    private int id;
    private String firstName, lastName, email, phone, nic, password;

    public UserModel(int id, String firstName, String lastName, String email, String phone, String nic, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.nic = nic;
        this.password = password;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getNic() { return nic; }
    public String getPassword() { return password; }
}
