package com.example.evaluacion2app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    private ListView lvdb;
    private ArrayList<Basedato1> lisdb;
    private ArrayAdapter<Basedato1> ada;
    private TextView textViewRUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textViewRUT = findViewById(R.id.textViewRUT);
        String rut = getIntent().getStringExtra("RUT");
        if (rut != null) {
            textViewRUT.setText("Tú rut: " + rut);
        } else {
            // Si no se recibe el RUT, mostrar un mensaje por defecto
            textViewRUT.setText("RUT no disponible");
        }

        // Inicialización del ListView
        lvdb = findViewById(R.id.listview);
        lisdb = new ArrayList<>();
        ada = new ArrayAdapter<>(MainActivity3.this, android.R.layout.simple_list_item_1, lisdb);
        lvdb.setAdapter(ada);

        // Llamada para cargar los datos en el ListView
        listardatos();

        // Configuración de los elementos del ListView
        lvdb.setOnItemClickListener((adapterView, view, position, id) -> {
            Basedato1 lbd = lisdb.get(position);
            AlertDialog.Builder a = new AlertDialog.Builder(MainActivity3.this);
            a.setCancelable(true);
            a.setTitle("Tus datos personales");
            String msg = "ID: " + lbd.getRut() + "\n\n";
            msg += "Nombre: " + lbd.getNom() + "\n\n";
            msg += "Apellido: " + lbd.getApp() + "\n\n";
            msg += "Fecha Nacimiento: " + lbd.getFecha() + "\n\n";
            msg += "Carrera: " + lbd.getCarrera() + "\n\n";
            a.setMessage(msg);
            a.show();
        });
    }

    private void listardatos() {
        // Obtener el RUT pasado desde el Intent
        String rut = getIntent().getStringExtra("RUT");

        if (rut != null) {
            // Conexión a Firebase
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbref = db.getReference(Basedato1.class.getSimpleName());

            dbref.orderByChild("rut").equalTo(rut)
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Basedato1 db1 = snapshot.getValue(Basedato1.class);
                            if (db1 != null) {
                                lisdb.add(db1);
                                ada.notifyDataSetChanged(); // Actualizar el ListView
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Basedato1 db1 = snapshot.getValue(Basedato1.class);
                            if (db1 != null) {
                                for (int i = 0; i < lisdb.size(); i++) {
                                    if (lisdb.get(i).getRut().equals(db1.getRut())) {
                                        lisdb.set(i, db1);
                                        ada.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            Basedato1 db1 = snapshot.getValue(Basedato1.class);
                            if (db1 != null) {
                                for (int i = 0; i < lisdb.size(); i++) {
                                    if (lisdb.get(i).getRut().equals(db1.getRut())) {
                                        lisdb.remove(i);
                                        ada.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            // No se necesita implementación para este caso
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            error.toException().printStackTrace();
                        }
                    });
        } else {
            // Si el RUT es nulo, puedes manejarlo o mostrar un mensaje
            textViewRUT.setText("RUT no disponible");
        }
    }

    // Boton volver al menú
    public void Volverinicio(View vista) {
        Intent Volverinicio = new Intent(this, MainActivity.class);
        startActivity(Volverinicio);
    }

    // Boton ir a modificar datos
    public void modificardatos(View vista) {
        // Obtener el RUT de la vista actual
        String rut = textViewRUT.getText().toString();
        // Crear un Intent y enviar el RUT a MainActivity5
        Intent modificardatos = new Intent(this, MainActivity5.class);
        modificardatos.putExtra("RUT", rut);
        startActivity(modificardatos);
    }

    // Boton ir a eliminar dato
    public void eliminardatos(View vista) {
        // Obtener el RUT de la vista actual
        String rut = textViewRUT.getText().toString();
        // Crear un Intent y enviar el RUT a MainActivity4
        Intent eliminardatos = new Intent(this, MainActivity4.class);
        eliminardatos.putExtra("RUT", rut);
        startActivity(eliminardatos);
    }
}

