package com.example.evaluacion2app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtrut;
    private Button btnbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("hola mundo");

        txtrut = findViewById(R.id.editTextText7);
        btnbuscar = findViewById(R.id.button3);
        btnbuscar.setEnabled(false);
        txtrut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEnabled = s.length() > 0;
                btnbuscar.setEnabled(isEnabled);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void Logearse(View view) {
        String rut = txtrut.getText().toString();

        if (!rut.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            intent.putExtra("RUT", rut);

            startActivity(intent);
        } else {
            Toast.makeText(this, "Por favor ingrese un RUT válido", Toast.LENGTH_SHORT).show();
        }
    }

    public void Registrarse(View vista) {
        Intent registrarseIntent = new Intent(this, MainActivity2.class);
        startActivity(registrarseIntent);
    }
}

