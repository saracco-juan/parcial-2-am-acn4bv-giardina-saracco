package com.example.glypha_primer_parcial_giardina_saracco.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.glypha_primer_parcial_giardina_saracco.R;

public class MainActivity extends AppCompatActivity {

    Button homeBtn, searchBtn, profileBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d("test-app", "Corre el emulador");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profileBtn = findViewById(R.id.btn_perfil);
        searchBtn = findViewById(R.id.btn_buscar);
        homeBtn = findViewById(R.id.btn_inicio);

        //Elementos creados dinamicamente
        createFavSeccion();

        applySelectedFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        applySelectedFromIntent(intent);
    }

    private void applySelectedFromIntent(Intent intent) {
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

    public void createFavSeccion() {

        LinearLayout favContainer = findViewById(R.id.fav_container);

        favContainer.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Seccion en desarrollo", Toast.LENGTH_SHORT).show());

        TextView favoritos = new TextView(this);
        favoritos.setTextColor(getColor(R.color.bg_yellow));
        favoritos.setText(getString(R.string.favoritos));
        favoritos.setTextSize(16);
        favoritos.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams textoParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
        );
        favoritos.setLayoutParams(textoParams);

        ImageView arrowIcon = new ImageView(this);
        arrowIcon.setImageResource(R.drawable.icon_arrow);
        arrowIcon.setColorFilter(getColor(R.color.bg_yellow));

        favContainer.addView(favoritos);
        favContainer.addView(arrowIcon);
    }

    public void changeColorBtn(Button btnProfile, Button btnSearch, Button btnHome, Button btnSelected) {

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

    public void goSearch(View view) {
        Intent search = new Intent(this, SearchActivity.class);
        search.putExtra("selected_tab", "search");
        startActivity(search);
    }

    public void goAdmin(View view){
        Intent admin = new Intent(this, AdminActivity.class);
        startActivity(admin);
    }

}
