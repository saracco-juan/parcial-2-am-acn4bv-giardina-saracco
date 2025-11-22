package com.example.glypha_primer_parcial_giardina_saracco.activities;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.example.glypha_primer_parcial_giardina_saracco.data.db.AdminSQLiteOpenHelper;


public class AdminActivity extends AppCompatActivity {

    private EditText et_id_fuente, et_nombre_fuente, et_tamanio_fuente, et_peso_fuente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_layout);

        et_id_fuente = (EditText) findViewById(R.id.et_id_fuente);
        et_nombre_fuente = (EditText) findViewById(R.id.et_nombre_fuente);
        et_peso_fuente = (EditText) findViewById(R.id.et_peso_fuente);
        et_tamanio_fuente = (EditText) findViewById(R.id.et_tamanio_fuente);

    }

    public void Registrar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase Database = admin.getWritableDatabase();

        String id = et_id_fuente.getText().toString();
        String nombre = et_nombre_fuente.getText().toString();
        String peso = et_peso_fuente.getText().toString();
        String tamanio = et_tamanio_fuente.getText().toString();

        if(!id.isEmpty() && !nombre.isEmpty() && !peso.isEmpty() && !tamanio.isEmpty()){

            if(ExisteFuentePorId(id)){
                Toast.makeText(this, "Ya existe una fuente con ese id", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues registro = new ContentValues();
            registro.put("id", id);
            registro.put("nombre", nombre);
            registro.put("peso", peso);
            registro.put("tamanio", tamanio);

            Database.insert("fuentes", null, registro);
            Database.close();

           limpiarCampos();
            Toast.makeText(this, "El registro fue exitoso!", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase Database = admin.getWritableDatabase();

        String id = et_id_fuente.getText().toString();

        if(!id.isEmpty() ){



                Cursor fila = Database.rawQuery("select nombre, peso, tamanio from fuentes where id=" + id, null);

                if (fila.moveToFirst()) {
                    et_nombre_fuente.setText(fila.getString(0));
                    et_peso_fuente.setText(fila.getString(1));
                    et_tamanio_fuente.setText(fila.getString(2));
                } else {
                    Toast.makeText(this, "No se encontro la fuente", Toast.LENGTH_SHORT).show();
                }
                Database.close();

        }else{
            Toast.makeText(this, "Debes llenar el id de la fuente para buscarla", Toast.LENGTH_SHORT).show();
            Database.close();
        }
    }

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase Database = admin.getWritableDatabase();

        String id = et_id_fuente.getText().toString();

        if(!id.isEmpty() ){
            int cantidad = Database.delete("fuentes", "id=" + id, null);
            Database.close();

            limpiarCampos();

            if(cantidad == 1){
                Toast.makeText(this, "El registro fue eliminado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se encontro la fuente", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes llenar el id de la fuente para eliminarla", Toast.LENGTH_SHORT).show();
            Database.close();
        }

    }

    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase Database = admin.getWritableDatabase();

        String id = et_id_fuente.getText().toString();
        String nombre = et_nombre_fuente.getText().toString();
        String peso = et_peso_fuente.getText().toString();
        String tamanio = et_tamanio_fuente.getText().toString();


        if(!id.isEmpty() && !nombre.isEmpty() && !peso.isEmpty() && !tamanio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("peso", peso);
            registro.put("tamanio", tamanio);

            int cantidad = Database.update("fuentes", registro, "id=" + id, null);
            Database.close();

            if(cantidad == 1){
                Toast.makeText(this, "El registro fue modificado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se encontro la fuente", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            Database.close();
        }
    }

    private void limpiarCampos(){
        et_id_fuente.setText("");
        et_nombre_fuente.setText("");
        et_peso_fuente.setText("");
        et_tamanio_fuente.setText("");
    }

    private boolean ExisteFuentePorId(String id) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "db_administracion", null, 1);
        SQLiteDatabase database = admin.getReadableDatabase();
        Cursor cursor = null;
        boolean existe = false;

        try {
            String[] columns = {"id"};
            String selection = "id = ?";
            String[] selectionArgs = {id};

            cursor = database.query("fuentes",
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );


            if (cursor.getCount() > 0) {
                existe = true;
            }

        } catch (Exception e) {

        } finally {

            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }

        return existe;
    }

    public void goHome(View view){
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("selected_tab", "home");
        startActivity(home);
    }
}
