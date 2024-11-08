package com.example.evaluacion2app;

public class Basedato1 {
    // Asignar variables
    private String rut;
    private String nom;
    private String app;
    private String fecha;
    private String carrera;

    // Constructor vacío requerido por Firebase
    public Basedato1() {
        // Firebase necesita un constructor vacío
    }

    // Constructor con parámetros
    public Basedato1(String rut, String nom, String app, String fecha, String carrera) {
        this.rut = rut;
        this.nom = nom;
        this.app = app;
        this.fecha = fecha;
        this.carrera = carrera;
    }

    // Getter
    public String getRut() {
        return rut;
    }

    public String getNom() {
        return nom;
    }

    public String getApp() {
        return app;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCarrera() {
        return carrera;
    }

    // Setter
    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    // Sobrescribir el método toString() para representar los datos en el ListView
    @Override
    public String toString() {
        return nom + " " + app; // Muestra nombre y apellido
    }
}
