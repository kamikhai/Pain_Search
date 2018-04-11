package com.example.rrota.pain_s;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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

/**
 * Created by EmilLatypov on 16.01.2018.
 * Активити которое выдвигает боковое меню
 */
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private static final String TAG = "DrawerActivity";


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());
        ImageView image_view=findViewById(R.id.imageView);
        final TextView inp_name = (TextView) findViewById(R.id.nav_user_name);
        final TextView inp_em = (TextView) findViewById(R.id.nav_user_email);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                inp_name.setText(user.usersurname+" "+user.username);
                inp_em.setText(user.email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(DrawerActivity.this, "Failed to load User.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(postListener);

        mUserListener = postListener;
        return super.onCreatePanelMenu(featureId, menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.main, new MainFragment());

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

private void displaySelectedScreen(int id, MainFragment mainFragment){
    Fragment fragment = null;
    switch (id){
        case R.id.main:
            fragment = new MainFragment();
            break;
        case R.id.pain:
            fragment = new PainFragment();
            break;
        case R.id.drug:
            fragment = new FragmentDrug();
            break;
        case R.id.off:
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Index.class));
            finish();
            break;
    }
    if (fragment != null){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
}
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       displaySelectedScreen(id, new MainFragment());
        return true;

    }


}
