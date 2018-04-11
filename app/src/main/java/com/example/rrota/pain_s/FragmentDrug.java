package com.example.rrota.pain_s;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class FragmentDrug extends Fragment {


    @Nullable
    Button noshpa;
    Button nurofen;
    Button voltaren;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_scrolling_drug, container, false);
        noshpa = (Button)v.findViewById(R.id.noshpa);
       noshpa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent1 = new Intent(getActivity(), ActivityNoshpa.class);
               startActivity(intent1);

           }
       });

        nurofen = (Button)v.findViewById(R.id.nurofen);
        nurofen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), ActivityNurofen.class);
                startActivity(intent2);
            }
        });

        voltaren = (Button)v.findViewById(R.id.voltaren);
        voltaren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), ActivityVoltaren.class);
                startActivity(intent3);
            }
        });

        return v;

    }







}

