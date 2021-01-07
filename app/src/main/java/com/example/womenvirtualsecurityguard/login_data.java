package com.example.womenvirtualsecurityguard;

public class login_data {
    public String username,mobilenumber;

    public login_data(String username, String mobilenumber) {
        this.username = username;
        this.mobilenumber = mobilenumber;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

}
