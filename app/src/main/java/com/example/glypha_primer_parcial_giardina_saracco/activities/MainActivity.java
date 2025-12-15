package com.example.glypha_primer_parcial_giardina_saracco.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.glypha_primer_parcial_giardina_saracco.adapters.FuentesAdapter;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.Fuente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private RecyclerView rvFavoritos;
    private FuentesAdapter fuentesAdapter;
    private List<Fuente> listaFavoritos;

    private String name;
    private String about;

    private String mail;

    private Button homeBtn, searchBtn, profileBtn;
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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

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

                                    name = ds.getData().get("name").toString();
                                    about = ds.getData().get("about").toString();
                                    mail = ds.getData().get("mail").toString();

                                }
                                //Cargar la informacion del usuario en la vista
                                loadUserData();
                                Toast.makeText(getApplicationContext(), "Nombre:"+name+" Uid"+currentUser.getUid(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

        profileBtn = findViewById(R.id.btn_perfil);
        searchBtn = findViewById(R.id.btn_buscar);
        homeBtn = findViewById(R.id.btn_inicio);

        // Configuración del RecyclerView para favoritos
        rvFavoritos = findViewById(R.id.rv_favoritos);
        rvFavoritos.setLayoutManager(new LinearLayoutManager(this));
        listaFavoritos = new ArrayList<>();
        fuentesAdapter = new FuentesAdapter(listaFavoritos, null); // No necesita listener aquí
        rvFavoritos.setAdapter(fuentesAdapter);

        cargarFavoritos();
        applySelectedFromIntent(getIntent());



    }

    private void cargarFavoritos() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("users").document(userId).collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaFavoritos.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Fuente fuente = document.toObject(Fuente.class);
                            listaFavoritos.add(fuente);
                        }
                        fuentesAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Error al cargar favoritos.", Toast.LENGTH_SHORT).show();
                    }
                });
        }
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

    public void handleLogOut(View view){

        if(mAuth == null) return;
        if(mAuth.getCurrentUser() == null) return;

        mAuth.signOut();

        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);

    }

    public void loadUserData(){

        TextView inputName = findViewById(R.id.txt_profile_name);

        inputName.setText(name);

        TextView inputMail = findViewById(R.id.txt_profile_mail);

        inputMail.setText(mail);

        TextView inputAbout = findViewById(R.id.acerca_de_texto);

        inputAbout.setText(about);

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
}