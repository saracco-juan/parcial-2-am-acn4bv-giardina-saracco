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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.example.glypha_primer_parcial_giardina_saracco.adapters.FuentesAdapter;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.Fuente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.User;

public class MainActivity extends BaseActivity {

    private RecyclerView rvFavoritos;
    private FuentesAdapter fuentesAdapter;
    private List<Fuente> listaFavoritos;

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

        //metodo traido desde BaseActivity
        initNavbarButtons();
        initFirebase();

        //Get usuario
        userLoged = (User) getIntent().getSerializableExtra("user");

        loadUserData();
        handleNavbar();

        // Configuración del RecyclerView para favoritos
        rvFavoritos = findViewById(R.id.rv_favoritos);
        rvFavoritos.setLayoutManager(new LinearLayoutManager(this));
        listaFavoritos = new ArrayList<>();
        fuentesAdapter = new FuentesAdapter(listaFavoritos, null); // No necesita listener aquí
        rvFavoritos.setAdapter(fuentesAdapter);

        cargarFavoritos();
        applySelectedFromIntent(getIntent());





    }

    public void loadUserData(){

        TextView inputName = findViewById(R.id.txt_profile_name);

        inputName.setText(userLoged.getName());

        TextView inputMail = findViewById(R.id.txt_profile_mail);

        inputMail.setText(userLoged.getMail());

        TextView inputAbout = findViewById(R.id.acerca_de_texto);

        inputAbout.setText(userLoged.getAbout());

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

    public void handleLogOut(View view){

        if(mAuth == null) return;
        if(mAuth.getCurrentUser() == null) return;

        mAuth.signOut();

        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);

    }


}