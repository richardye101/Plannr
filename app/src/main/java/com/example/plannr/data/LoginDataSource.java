package com.example.plannr.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plannr.data.model.LoggedInUser;
import com.example.plannr.models.User;
import com.example.plannr.services.DatabaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    DatabaseConnection db;
    HashMap users = new HashMap<>();

    public LoginDataSource(){
        db = DatabaseConnection.getInstance();
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

            // To obtain the data from the users node once and store it in the users Hashmap
            db.ref.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        HashMap users = (HashMap) task.getResult().getValue();
                        onItemsObtained(users);
                        // Used to create a new user if their account is not found
                        if(users.get(User.generateHash(username)) == null){
                            User user = new User(username, hashPassword(password));
                            Log.d("Adding new user", user.getUsername());
                            db.ref.child("users").child(User.generateHash(username)).setValue(user);
                        }
                        Log.d("password check", String.valueOf(samePassword(username, password)));
                    }
                }
            });

//            Log.d("stored users", String.valueOf(users));
//            Log.i("theUser", String.valueOf(users.get(User.generateID(username))));

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

    public void onItemsObtained(HashMap users) {
//        users.clear(); // To remove old Data
        this.users.putAll(users);
        Log.d("stored users", String.valueOf(users));
        // Continue your own logic...
    }
    public String hashPassword(String password){
//        // generate salt for hashing the password
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        // hash the password
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        byte[] hash = factory.generateSecret(spec).getEncoded();
//        return hash.toString();

//        Another method of hashing
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));


        String hashed = encodedhash.toString();
        hashed = User.generateHash(password);
        return hashed;
    }

    private boolean samePassword(String username, String password){
        HashMap user = (HashMap) users.get(User.generateHash(username));
        Log.d("theUserClass", String.valueOf(users.get(User.generateHash(username))));

        if(user == null){
            Log.e("user error", "No user found");
            return false;
        }
        String hash = hashPassword(password);

        Log.d("entered password", String.valueOf(hash));
        Log.d("stored password", String.valueOf(user.get("password")));

        if(hash.equals((String) user.get("password"))){
            return true;
        }

        return false;
    }
}