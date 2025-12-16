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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends BaseActivity {

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

        //metodo traido desde BaseActivity
        initNavbarButtons();
        initFirebase();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
            return;
        }else{
            db
                    .collection("users")
                    .whereEqualTo("uid",currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){

                                QuerySnapshot result = task.getResult();

                                for (DocumentSnapshot ds: result.getDocuments()) {

                                    String name = ds.getData().get("name").toString();
                                    String about = ds.getData().get("about").toString();
                                    String mail = ds.getData().get("mail").toString();
                                    String rol = ds.getData().get("rol").toString();

                                    userLoged = new User(name, rol, mail, about);

                                }

                                handleNavbar();
                                applySelectedFromIntent(getIntent());
                            }
                        }
                    });
        }

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

}