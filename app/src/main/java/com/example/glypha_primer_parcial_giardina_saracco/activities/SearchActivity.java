package com.example.glypha_primer_parcial_giardina_saracco.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.example.glypha_primer_parcial_giardina_saracco.adapters.FuentesAdapter;
import com.example.glypha_primer_parcial_giardina_saracco.data.db.AdminSQLiteOpenHelper;
import com.example.glypha_primer_parcial_giardina_saracco.data.model.Fuente;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button profileBtn;
    private Button homeBtn;
    private Button searchBtn;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FuentesAdapter adapter;
    private List<Fuente> listaCompletaFuentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchView = findViewById(R.id.sv_1);
        recyclerView = findViewById(R.id.rv_fuentes);
        listaCompletaFuentes = new ArrayList<>();
        profileBtn = findViewById(R.id.btn_perfil);
        searchBtn = findViewById(R.id.btn_buscar);
        homeBtn = findViewById(R.id.btn_inicio);

        // 1. Cargar todas las fuentes desde la BD
        cargarFuentesDesdeDB();

        // 2. Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FuentesAdapter(listaCompletaFuentes);
        recyclerView.setAdapter(adapter);

        // 3. Configurar el listener del SearchView
        setupSearchView();

        applySelectedFromIntent(getIntent());

    }

    private void cargarFuentesDesdeDB() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT nombre FROM fuentes", null);

        if (cursor.moveToFirst()) {
            do {
                // Asumiendo que la columna 0 es 'nombre'
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
                // Opcional: acción cuando el usuario presiona "buscar"
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // ¡La magia sucede aquí!
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        List<Fuente> filteredList = new ArrayList<>();

        for (Fuente item : listaCompletaFuentes) {
            // Filtrar por nombre, ignorando mayúsculas/minúsculas
            if (item.getNombre().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Actualizar el adaptador con la nueva lista filtrada
        adapter.filterList(filteredList);
    }

    public void goHome(View view) {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("selected_tab", "home");
        startActivity(home);
    }


    private void applySelectedFromIntent(Intent intent) {
        if (intent == null) return;
        String selected = intent.getStringExtra("selected_tab");

        // Asignar el botón "search" como seleccionado por defecto si no viene nada.
        if (selected == null) {
            selected = "search";
        }

        Button selectedBtn = null;
        if ("home".equals(selected)) selectedBtn = homeBtn;
        else if ("search".equals(selected)) selectedBtn = searchBtn;
        else if ("profile".equals(selected)) selectedBtn = profileBtn;

        // Asegurarse de que los botones existan antes de llamar a changeColorBtn
        if (selectedBtn != null && profileBtn != null && searchBtn != null && homeBtn != null) {
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
}
