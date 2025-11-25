package com.example.glypha_primer_parcial_giardina_saracco.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.glypha_primer_parcial_giardina_saracco.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textCatCards();

        textTendenciasCards();

        handleFontTest();

        textSeccionUnderline();
    }

    private void textSeccionUnderline(){

        View seccionTendencias = findViewById(R.id.tendencias_underline);

        View seccionMasUsadas = findViewById(R.id.mas_usadas_underline);

        ((TextView) seccionTendencias.findViewById(R.id.tilte_underline)).setText(getString(R.string.title_inspiracion_semanal_tendencias));
        ((TextView) seccionMasUsadas.findViewById(R.id.tilte_underline)).setText(getString(R.string.title_inspiracion_semanal_mas_usadas));
    }

    private void textCatCards(){

        int[] catCardIds = {
                R.id.cardModerna,
                R.id.cardElegante,
                R.id.cardClasica,
                R.id.cardCreativa
        };

        int[] catLabelRes = {
                R.string.txt_cat_moderna,
                R.string.txt_cat_elegante,
                R.string.txt_cat_clasica,
                R.string.txt_cat_creativa
        };


        for (int i = 0; i < catCardIds.length; i++) {
            View card = findViewById(catCardIds[i]);
            TextView txt = card.findViewById(R.id.txtCard);
            txt.setText(getString(catLabelRes[i]));
        }
    }

    private void textTendenciasCards() {

        int[] fontCardIds = {
                R.id.cardRoboto,
                R.id.cardPlayFair,
                R.id.cardMontserrat,
                R.id.cardSansation
        };

        int[] fontListIds = {
                R.font.roboto,
                R.font.playfair_display,
                R.font.montserrat,
                R.font.sansation
        };

        int[] cardLabels = {
                R.string.txt_font_roboto,
                R.string.txt_font_playfair,
                R.string.txt_font_montserrat,
                R.string.txt_font_sansation
        };

        for (int i = 0; i < fontCardIds.length; i++) {

            View card = findViewById(fontCardIds[i]);
            Typeface font = ResourcesCompat.getFont(this, fontListIds[i]);

            ((TextView) card.findViewById(R.id.txtCard)).setText(getString(cardLabels[i]));
            ((TextView) card.findViewById(R.id.txtCard)).setTypeface(font);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    changeFontTxt(font);
                }
            });
        }
    }

    private void changeFontTxt(Typeface font){
        TextView font_test_txt = findViewById(R.id.font_txtView);
        font_test_txt.setTypeface(font);
    }

    private void handleFontTest(){

        EditText inputTxt = findViewById(R.id.inputFont);

        TextView font_test_txt = findViewById(R.id.font_txtView);

        inputTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    font_test_txt.setText(s.toString());
                } else {
                    //Si el input esta vacio seteo el texto por defecto
                    font_test_txt.setText(getString(R.string.probar_fuente_txt));
                }
            }
        });
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

    public void goHome(View view){
        Intent home = new Intent(this, HomeActivity.class);
        startActivity(home);
    }

    public void goProfile(View view){
        Intent profile = new Intent(this, MainActivity.class);
        profile.putExtra("selected_tab", "profile");
        startActivity(profile);
    }


}