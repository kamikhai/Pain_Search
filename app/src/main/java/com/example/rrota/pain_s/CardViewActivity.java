package com.example.rrota.pain_s;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewActivity extends Activity {

    TextView disease;
    TextView doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.disease_cards);
        disease = (TextView)findViewById(R.id.disease);
        doctors = (TextView)findViewById(R.id.doctors);

        disease.setText("20");
        doctors.setText("1");
    }
}
