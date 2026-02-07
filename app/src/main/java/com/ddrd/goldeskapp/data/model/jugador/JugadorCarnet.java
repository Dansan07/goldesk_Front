package com.ddrd.goldeskapp.data.model.jugador;

public class JugadorCarnet {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String urlFoto;
    private Boolean esDelegado;

    // Datos del Contexto (De las relaciones)
    private String nombreEquipo;
    private String nombreTorneo;
    private String categoriaTorneo; // Ej: Libre, Veteranos, etc.
    private Integer idInscripcion;
    private String fechaInscripcion;

    public JugadorCarnet(String cedula, String nombre, String apellidos, String telefono, String email, String urlFoto, Boolean esDelegado, String nombreEquipo, String nombreTorneo, String categoriaTorneo, Integer idInscripcion, String fechaInscripcion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.urlFoto = urlFoto;
        this.esDelegado = esDelegado;
        this.nombreEquipo = nombreEquipo;
        this.nombreTorneo = nombreTorneo;
        this.categoriaTorneo = categoriaTorneo;
        this.idInscripcion = idInscripcion;
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Boolean getEsDelegado() {
        return esDelegado;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public String getCategoriaTorneo() {
        return categoriaTorneo;
    }

    public Integer getIdInscripcion() {
        return idInscripcion;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }
}
