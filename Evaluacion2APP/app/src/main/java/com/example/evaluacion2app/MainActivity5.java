package com.example.evaluacion2app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity5 extends AppCompatActivity {

    private EditText txtNom, txtApellido, txtFecha, txtCarrera;
    private String rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        // Inicialización de los campos EditText
        txtNom = findViewById(R.id.editTextNom);
        txtApellido = findViewById(R.id.editTextApellido);
        txtFecha = findViewById(R.id.editTextFecha);
        txtCarrera = findViewById(R.id.editTextCarrera);

        // Recibir el RUT desde el Intent de MainActivity3
        Intent intent = getIntent();
        rut = intent.getStringExtra("RUT");

        // Mostrar el RUT recibido para verificarlo
        Log.d("MainActivity5", "RUT recibido: " + rut);

        // Si el RUT es válido, se carga la información desde Firebase
        if (rut != null && !rut.isEmpty()) {
            cargarDatosUsuario(rut);
        } else {
            txtNom.setText("RUT no válido");
        }
    }

    // Método para cargar los datos del usuario desde Firebase usando el RUT
    private void cargarDatosUsuario(String rut) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Basedato1"); // Asegúrate de que el nombre sea correcto

        // Se obtiene la referencia a todos los usuarios
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;

                // Iterar sobre todos los usuarios
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userRut = userSnapshot.child("rut").getValue(String.class);

                    // Verifica si el RUT coincide con el buscado
                    Log.d("MainActivity5", "RUT de usuario encontrado: " + userRut); // Verificar RUT de cada usuario

                    if (userRut != null && userRut.equals(rut)) {
                        // Si encontramos el usuario, se obtiene y muestra su información
                        String nombre = userSnapshot.child("nom").getValue(String.class);
                        String apellido = userSnapshot.child("apellido").getValue(String.class);
                        String fecha = userSnapshot.child("fecha").getValue(String.class);
                        String carrera = userSnapshot.child("carrera").getValue(String.class);

                        // Mostrar los datos en los EditText
                        txtNom.setText(nombre);
                        txtApellido.setText(apellido);
                        txtFecha.setText(fecha);
                        txtCarrera.setText(carrera);

                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    // Si no se encuentra el usuario, mostrar un mensaje
                    txtNom.setText("Usuario no encontrado");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores en la conexión
                txtNom.setText("Error en la conexión");
            }
        });
    }

    public void modificar(View vista) {
        String nombre = txtNom.getText().toString();
        String apellido = txtApellido.getText().toString();
        String fecha = txtFecha.getText().toString();
        String carrera = txtCarrera.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Basedato1");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userRut = userSnapshot.child("rut").getValue(String.class);

                    if (userRut != null && userRut.equals(rut)) {
                        // Actualizar los datos en Firebase
                        userSnapshot.getRef().child("nom").setValue(nombre);
                        userSnapshot.getRef().child("apellido").setValue(apellido);
                        userSnapshot.getRef().child("fecha").setValue(fecha);
                        userSnapshot.getRef().child("carrera").setValue(carrera);
                        break;
                    }
                }

                txtNom.setText("Datos actualizados");

                Intent intent = new Intent(MainActivity5.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                txtNom.setText("Error en la conexión");
            }
        });
    }

    public void Volver_inicio(View vista) {
        Intent Volver_inicio = new Intent(this, MainActivity.class);
        startActivity(Volver_inicio);
    }
}
