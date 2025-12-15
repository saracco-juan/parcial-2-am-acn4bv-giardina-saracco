package com.example.glypha_primer_parcial_giardina_saracco.activities;


import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;

    protected Button homeBtn, searchBtn, profileBtn;

    protected void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore. getInstance();
    }



}