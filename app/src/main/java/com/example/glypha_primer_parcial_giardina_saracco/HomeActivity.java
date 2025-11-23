package com.example.glypha_primer_parcial_giardina_saracco;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        fontClickListeners();

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

        View cardModerna = findViewById(R.id.cardModerna);
        View cardClasica = findViewById(R.id.cardClasica);
        View cardElegante = findViewById(R.id.cardElegante);
        View cardCreativa = findViewById(R.id.cardCreativa);



        ((TextView) cardModerna.findViewById(R.id.txtCard)).setText(getString(R.string.txt_cat_moderna));
        ((TextView) cardClasica.findViewById(R.id.txtCard)).setText(getString(R.string.txt_cat_clasica));
        ((TextView) cardElegante.findViewById(R.id.txtCard)).setText(getString(R.string.txt_cat_elegante));
        ((TextView) cardCreativa.findViewById(R.id.txtCard)).setText(getString(R.string.txt_cat_creativa));


    }

    private void textTendenciasCards() {

        View cardRoboto = findViewById(R.id.cardRoboto);
        View cardPlayFair = findViewById(R.id.cardPlayFair);
        View cardMontserrat = findViewById(R.id.cardMontserrat);
        View cardSansation = findViewById(R.id.cardSansation);

        Typeface roboto   = ResourcesCompat.getFont(this, R.font.roboto);
        Typeface playfair = ResourcesCompat.getFont(this, R.font.playfair_display);
        Typeface montserrat = ResourcesCompat.getFont(this, R.font.montserrat);
        Typeface sansation = ResourcesCompat.getFont(this, R.font.sansation);

        ((TextView) cardRoboto.findViewById(R.id.txtCard)).setText(getString(R.string.txt_font_roboto));
        ((TextView) cardRoboto.findViewById(R.id.txtCard)).setTypeface(roboto);

        ((TextView) cardPlayFair.findViewById(R.id.txtCard)).setText(getString(R.string.txt_font_playfair));
        ((TextView) cardPlayFair.findViewById(R.id.txtCard)).setTypeface(playfair);
        ((TextView) cardPlayFair.findViewById(R.id.txtCard)).setTextSize(12);

        ((TextView) cardMontserrat.findViewById(R.id.txtCard)).setText(getString(R.string.txt_font_montserrat));
        ((TextView) cardMontserrat.findViewById(R.id.txtCard)).setTypeface(montserrat);

        ((TextView) cardSansation.findViewById(R.id.txtCard)).setText(getString(R.string.txt_font_sansation));
        ((TextView) cardSansation.findViewById(R.id.txtCard)).setTypeface(sansation);
    }

    private void fontClickListeners(){

        View cardRoboto = findViewById(R.id.cardRoboto);
        View cardPlayFair = findViewById(R.id.cardPlayFair);
        View cardMontserrat = findViewById(R.id.cardMontserrat);
        View cardSansation = findViewById(R.id.cardSansation);

        cardRoboto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typeface roboto   = ResourcesCompat.getFont(getApplicationContext(), R.font.roboto);
                changeFontTxt(roboto);
            }
        });

        cardPlayFair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typeface playFair   = ResourcesCompat.getFont(getApplicationContext(), R.font.playfair_display);
                changeFontTxt(playFair);
            }
        });

        cardMontserrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typeface montserrat   = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);
                changeFontTxt(montserrat);
            }
        });

        cardSansation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typeface sansation   = ResourcesCompat.getFont(getApplicationContext(), R.font.sansation);
                changeFontTxt(sansation);
            }
        });

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
}