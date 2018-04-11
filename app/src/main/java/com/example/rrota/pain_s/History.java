package com.example.rrota.pain_s;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by EmilLatypov on 16.02.2018.
 * Активити "История болезней"
 */
public class History extends AppCompatActivity {
    private LinkedList<Disease> diseases;
    private RecyclerView rv;
    private FirebaseAuth mAuth;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;

    ImageView im;
    TextView tx;

    RVAdapter adapter;
    private static final String TAG = "History";
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("disease").child(user.getUid());
        Log.d("initializte ", mPostReference.getKey());
        im=findViewById(R.id.imageView3);
        tx=findViewById(R.id.textView);


    }
//    private void initializeData(){
     @Override
     public void onStart() {
         super.onStart();
         diseases = new LinkedList<>();
         Log.d("initializte ", "start");
         ValueEventListener postListener = new ValueEventListener() {

             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 Log.d("Change ", "started");
                 // Get Post object and use the values to update the UI
//                User user = dataSnapshot.getValue(User.class);
                 Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
                 Iterator<DataSnapshot> iterator1 = iterator.iterator();
                 diseases.clear();
                 while (iterator1.hasNext()) {
                     DataSnapshot next = iterator1.next();
                     Disease value = next.getValue(Disease.class);
                    diseases.addFirst(value);
                }
                adapter.notifyDataSetChanged();

                 if (diseases.size() !=0) {
                     im.setVisibility(View.GONE);
                     tx.setVisibility(View.GONE);
                 }
                 // [START_EXCLUDE]
//                mAuthorView.setText(user.username);
                 // [END_EXCLUDE]
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 // Getting Post failed, log a message
                 Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                 // [START_EXCLUDE]
                 Toast.makeText(History.this, "Failed to load post.",
                         Toast.LENGTH_SHORT).show();
                 // [END_EXCLUDE]
             }
         };

         mPostReference.addValueEventListener(postListener);
         // [END post_value_event_listener]

         // Keep copy of post listener so we can remove it when app stops
         mPostListener = postListener;
//         diseases=new ArrayList<Disease>();
//         diseases.add(new Disease("1","123"));

         adapter = new RVAdapter(diseases);
         rv.setAdapter(adapter);
         adapter.notifyDataSetChanged();

     }

//    private void initializeAdapter(){
//        RVAdapter adapter = new RVAdapter(diseases);
//        rv.setAdapter(adapter);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
