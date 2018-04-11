package com.example.rrota.pain_s;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Disease {
    public String date;
    public String disease;
    public String doctors;

    public Disease() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Disease(String date, String disease, String doctors) {
        this.date= date;
        this.disease = disease;
        this.doctors = doctors;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("disease", disease);
        result.put("doctors", doctors);
        return result;
    }
}
