package com.example.plannr.data;

import android.util.Log;
import android.widget.Toast;

import com.example.plannr.data.model.LoggedInUser;
import com.example.plannr.models.User;
import com.example.plannr.services.DatabaseConnection;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    DatabaseConnection db;
    public LoginDataSource(){
        db = DatabaseConnection.getInstance();
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            Log.d("data", String.valueOf(db.ref.child("users").get()));

            if(db.ref.child("users").child(username) == null){
                User user = new User(username, password);
                db.ref.child("users").child(username).setValue(user);
            };

            LoggedInUser user =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}