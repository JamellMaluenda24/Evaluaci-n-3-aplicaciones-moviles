package com.example.evaluacion2app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;  // Importar TextView
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        String rut = getIntent().getStringExtra("RUT");

        if (rut != null) {
            // Obtener el TextView y actualizar el texto con el RUT
            TextView rutTextView = findViewById(R.id.rutTextView);
            rutTextView.setText(rut);
        }
    }

    public void confirmarEliminacion(View vista) {
        String rut = getIntent().getStringExtra("RUT");

        if (rut != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Estás seguro de que deseas eliminar tu RUT?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Si el usuario confirma, eliminar el RUT de Firebase
                            eliminarRut(rut);
                        }
                    })
                    .setNegativeButton("No", null)  // Si el usuario no confirma, no hacer nada
                    .show();
        }
    }

    private void eliminarRut(String rut) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Basedato1"); // Ruta hacia Basedato1

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean encontrado = false;
                Log.d("DEBUG", "Iniciando búsqueda del RUT: " + rut);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String rutEnDB = snapshot.child("rut").getValue(String.class);

                    Log.d("DEBUG", "Comparando con RUT en DB: " + rutEnDB);

                    if (rutEnDB != null && rutEnDB.equals(rut)) {
                        // Si el RUT coincide, eliminamos el nodo completo
                        snapshot.getRef().removeValue();
                        encontrado = true;
                        Log.d("DEBUG", "RUT encontrado y eliminado: " + rutEnDB);
                        break;
                    }
                }

                if (encontrado) {
                    // Redirigir a la pantalla anterior
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    startActivity(intent);
                } else {
                    // Si no se encontró el RUT, mostrar mensaje de error
                    Toast.makeText(MainActivity4.this, "RUT no encontrado en Basedato1", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "RUT no encontrado en Basedato1: " + rut);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar error de la base de datos
                Toast.makeText(MainActivity4.this, "Error en la consulta", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", "Error en la consulta: " + databaseError.getMessage());
            }
        });
    }


    // Botón volver a menu
    public void Volver_menu4(View vista) {
        Intent Volver_menu4 = new Intent(this, MainActivity3.class);
        startActivity(Volver_menu4);
    }
}
