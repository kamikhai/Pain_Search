package com.example.rrota.pain_s;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String usersurname;
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String usersurname, String username, String email) {
        this.usersurname = usersurname;
        this.username = username;
        this.email = email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("usersurname", usersurname);
        result.put("username", username);
        result.put("email", email);

        return result;
    }
}
