package com.example.glypha_primer_parcial_giardina_saracco.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.glypha_primer_parcial_giardina_saracco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void handleRegster(View view){

        EditText registerName = findViewById(R.id.name_register_input);
        EditText registerMail = findViewById(R.id.mail_register_input);
        EditText registerAbout = findViewById(R.id.about_register_input);
        EditText registerPassword = findViewById(R.id.password_register_input);

        String name = registerName.getText().toString();
        String mail = registerMail.getText().toString();
        String about = registerAbout.getText().toString();
        String password = registerPassword.getText().toString();

        if(mAuth==null) return;

        if(validateFieldEmpty(name) || validateFieldEmpty(mail) || validateFieldEmpty(about) || validateFieldEmpty(password)) return;

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String uid = task.getResult().getUser().getUid();

                    HashMap<String, String> user = new HashMap();
                    user.put("name",name);
                    user.put("uid",uid);
                    user.put("about", about);
                    user.put("mail",mail);

                    db
                        .collection("users")
                        .add(user)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class );
                                    startActivity(mainActivity);
                                }
                            }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "Ocurrio un error al registrarse. Por favor intente de nuevo", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public boolean validateFieldEmpty(String field){

        return field.isEmpty();
    }
}