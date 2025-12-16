package com.example.glypha_primer_parcial_giardina_saracco.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.example.glypha_primer_parcial_giardina_saracco.adapters.FuentesAdapter;
import com.example.glypha_primer_parcial_giardina_saracco.data.db.AdminSQLiteOpenHelper;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.Fuente;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements FuentesAdapter.OnFavoriteClickListener {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FuentesAdapter adapter;
    private List<Fuente> listaCompletaFuentes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //metodo traido desde BaseActivity
        initNavbarButtons();
        initFirebase();


        searchView = findViewById(R.id.sv_1);
        recyclerView = findViewById(R.id.rv_fuentes);
        listaCompletaFuentes = new ArrayList<>();
        profileBtn = findViewById(R.id.btn_perfil);
        searchBtn = findViewById(R.id.btn_buscar);
        homeBtn = findViewById(R.id.btn_inicio);

        cargarFuentesDesdeDB();

        //Get usuario
        userLoged = (User) getIntent().getSerializableExtra("user");

        handleNavbar();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Pasamos 'this' como listener
        adapter = new FuentesAdapter(listaCompletaFuentes, this);
        recyclerView.setAdapter(adapter);

        setupSearchView();
        applySelectedFromIntent(getIntent());
    }


    @Override
    public void onFavoriteClick(Fuente fuente) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Guardar en la subcolección 'favorites' del usuario actual
            db.collection("users").document(userId)
              .collection("favorites").add(fuente) // 'add' crea un documento con ID automático
              .addOnSuccessListener(documentReference -> {
                  Toast.makeText(SearchActivity.this, "Añadido a favoritos: " + fuente.getNombre(), Toast.LENGTH_SHORT).show();
              })
              .addOnFailureListener(e -> {
                  Toast.makeText(SearchActivity.this, "Error al guardar favorito", Toast.LENGTH_SHORT).show();
              });
        } else {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarFuentesDesdeDB() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT nombre FROM fuentes", null);

        if (cursor.moveToFirst()) {
            do {
                listaCompletaFuentes.add(new Fuente(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        List<Fuente> filteredList = new ArrayList<>();

        for (Fuente item : listaCompletaFuentes) {
            if (item.getNombre().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

}