package com.example.glypha_primer_parcial_giardina_saracco;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        //Elementos creados dinamicamente

        createFavSeccion();
    

        profileBtn = (Button) findViewById(R.id.btn_perfil);
        searchBtn = (Button) findViewById(R.id.btn_buscar);
        homeBtn = (Button) findViewById(R.id.btn_inicio);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hiciste click en el boton Perfil.", Toast.LENGTH_SHORT).show();

                changeColorBtn(profileBtn, searchBtn, homeBtn, profileBtn);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Seccion Buscar en construccion.", Toast.LENGTH_SHORT).show();

                changeColorBtn(profileBtn, searchBtn, homeBtn, searchBtn);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Seccion Inicio en construccion.", Toast.LENGTH_SHORT).show();

                changeColorBtn(profileBtn, searchBtn, homeBtn, homeBtn);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void createFavSeccion(){

        LinearLayout favContainer = findViewById(R.id.fav_container);

        favContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Seccion en desarrollo", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void changeColorBtn(Button btnProfile, Button btnSearch, Button btnHome, Button btnSelected){

        Button[] buttons = {btnProfile, btnSearch, btnHome};

        for(Button btn : buttons){

            if(btn == btnSelected){

                btn.setTextColor(getColor(R.color.blue));
                Drawable[] icons = btn.getCompoundDrawables();
                Drawable icon = icons[1];
                icon.setTint(getColor(R.color.blue));

            }else{

                btn.setTextColor(getColor(R.color.black));
                Drawable[] icons = btn.getCompoundDrawables();
                Drawable icon = icons[1];
                icon.setTint(getColor(R.color.black));
            }

        }

    }

}
