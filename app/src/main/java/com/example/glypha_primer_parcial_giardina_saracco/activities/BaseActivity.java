package com.example.glypha_primer_parcial_giardina_saracco.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;

    protected Button homeBtn, searchBtn, profileBtn;

    protected User userLoged;

    protected void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore. getInstance();
    }

    protected void initNavbarButtons() {
        profileBtn = findViewById(R.id. btn_perfil);
        searchBtn = findViewById(R.id. btn_buscar);
        homeBtn = findViewById(R.id. btn_inicio);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        applySelectedFromIntent(intent);
    }

    protected void applySelectedFromIntent(Intent intent) {
        if (intent == null) return;
        String selected = intent.getStringExtra("selected_tab");
        if (selected == null) return;

        Button selectedBtn = null;
        if ("home".equals(selected)) selectedBtn = homeBtn;
        else if ("search".equals(selected)) selectedBtn = searchBtn;
        else if ("profile".equals(selected)) selectedBtn = profileBtn;

        if (selectedBtn != null) {
            changeColorBtn(profileBtn, searchBtn, homeBtn, selectedBtn);
        }
    }

    protected void changeColorBtn(Button btnProfile, Button btnSearch, Button btnHome, Button btnSelected) {
        Button[] buttons = {btnProfile, btnSearch, btnHome};
        for (Button btn : buttons) {
            if (btn == null) continue;
            if (btn == btnSelected) {
                btn.setTextColor(getColor(R.color.blue));
                Drawable[] icons = btn.getCompoundDrawables();
                Drawable icon = icons[1];
                if (icon != null) {
                    icon = icon.mutate();
                    icon.setTint(getColor(R.color.blue));
                    btn.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
                }
            } else {
                btn.setTextColor(getColor(R.color.black));
                Drawable[] icons = btn.getCompoundDrawables();
                Drawable icon = icons[1];
                if (icon != null) {
                    icon = icon.mutate();
                    icon.setTint(getColor(R.color.black));
                    btn.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
                }
            }
        }
    }

    public void handleNavbar (){

        Button btn_admin = findViewById(R.id.btn_admin);

        if(userLoged.getRol().equals("cliente")){
            btn_admin.setVisibility(View.GONE);
        }

    }

    public void goSearch(View view) {
        Intent search = new Intent(this, SearchActivity.class);
        search.putExtra("selected_tab", "search");

        search.putExtra("user", userLoged);
        startActivity(search);
    }

    public void goAdmin(View view){
        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }

    public void goHome(View view){
        Intent home = new Intent(this, HomeActivity.class);
        home.putExtra("user", userLoged);
        startActivity(home);
    }

    public void goProfile(View view){
        Intent profile = new Intent(this, MainActivity.class);
        profile.putExtra("selected_tab", "profile");
        profile.putExtra("user", userLoged);
        startActivity(profile);
    }
}