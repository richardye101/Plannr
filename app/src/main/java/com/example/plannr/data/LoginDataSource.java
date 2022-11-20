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
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

interface LoginCallback{
    void isAuthenticated(boolean authed, User user);
}
/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    DatabaseConnection db;
    HashMap users = new HashMap<>();
    LoggedInUser user;

    public LoginDataSource(){
        db = DatabaseConnection.getInstance();
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

//            Log.d("stored users", String.valueOf(users));
//            Log.i("theUser", String.valueOf(users.get(User.generateID(username))));

            authenticate(username, password, new LoginCallback() {
                @Override
                public void isAuthenticated(boolean authed, User userObj) {
                    if(authed){
                        user = new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                userObj.getName());
                        Log.d("set user", String.valueOf(user));
                    }
                }
            });

            Log.d("final user", String.valueOf(user));
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void authenticate(String username, String enteredPassword, LoginCallback callback){
        // To obtain the data from the users node once and store it in the users Hashmap
        db.ref.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    HashMap users = (HashMap) task.getResult().getValue();
                    onUsersObtained(users);
                    User user = getUser(username);
                    // Used to create a new user if their account is not found
//                    if(users.get(User.generateHash(username)) == null){
//                        User user = new User(username, hashPassword(enteredPassword));
//                        Log.d("Adding new user", user.getUsername());
//                        db.ref.child("users").child(User.generateHash(username)).setValue(user);
//                    }
                    Log.d("password check", String.valueOf(samePassword(username, enteredPassword)));

                    if(samePassword(username, enteredPassword)){
                        callback.isAuthenticated(true, user);
                    }
                    else{
                        callback.isAuthenticated(false, user);
                    }
                }
            }
        });
    }
    public void logout() {
        // TODO: revoke authentication
    }

    public void onUsersObtained(HashMap users) {
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
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] encodedhash = digest.digest(
//                password.getBytes(StandardCharsets.UTF_8));


//        String hashed = encodedhash.toString();
        String hashed = User.generateHash(password);
        return hashed;
    }
    public User getUser(String username){
        HashMap userMap = (HashMap) users.get(User.generateHash(username));
//        Log.d("theUserClass", String.valueOf(users.get(User.generateHash(username))));
        User user = new User((String) userMap.get("username"), (String) userMap.get("password"));
        if(user == null){
            Log.e("user error", "No user found");
            return null;
        }
        return user;
    }
    private boolean samePassword(String username, String password){
        User user = getUser(username);
        String hash = hashPassword(password);

        Log.d("entered password", String.valueOf(hash));
        Log.d("stored password", String.valueOf(user.getPassword()));

        if(hash.equals(user.getPassword())){
            return true;
        }
        return false;
    }
}