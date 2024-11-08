package com.example.evaluacion2app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//importados
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;


public class MainActivity2 extends AppCompatActivity {
    private EditText txtrut, txtnom,txtapp,txtfecha,txtcarrera;
    private Button botonregistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("hola mundo");


        txtrut = (EditText)findViewById(R.id.editTextText);
        txtnom = (EditText)findViewById(R.id.editTextNom);
        txtapp = (EditText)findViewById(R.id.editTextApellido);
        txtfecha = (EditText)findViewById(R.id.editTextFecha);
        txtcarrera = (EditText)findViewById(R.id.editTextCarrera);
        botonregistrar = (Button)findViewById(R.id.button5);
    }

    public void botonregistrar(View view) {
        String rut = txtrut.getText().toString();
        String nom = txtnom.getText().toString();
        String apellido = txtapp.getText().toString();
        String fecha = txtfecha.getText().toString();
        String carrera = txtcarrera.getText().toString();
        // Conexión a Firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Basedato1.class.getSimpleName());
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Basedato1 db1 = new Basedato1(rut, nom,apellido,fecha,carrera);
                dbref.push().setValue(db1);

                txtrut.setText("");
                txtnom.setText("");
                txtapp.setText("");
                txtfecha.setText("");
                txtcarrera.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });
    }
    //Boton volver al menu
    public void Volver(View vista) {
        Intent Volver = new Intent(this, MainActivity.class);
        startActivity(Volver);
    }
}
