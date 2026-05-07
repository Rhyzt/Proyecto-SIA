package entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Campaña implements procesamiento.Validadores {
    private String idCampaña; // nombreCampaña + fechaCampaña como identificador
    private String nombreCampaña;
    private String ubicacion;
    private LocalDate fechaCampaña;
    private int metaDonaciones; // Se usaran en ml
    
    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public Campaña(String nombreCampaña, String ubicacion, String fechaCampaña, int metaDonaciones) {
        this.idCampaña = nombreCampaña.trim() + "_" + fechaCampaña; // Se construye el identificador de la Campaña
        this.nombreCampaña = nombreCampaña;
        this.ubicacion = ubicacion;
        this.metaDonaciones = metaDonaciones;
        this.fechaCampaña = LocalDate.parse(fechaCampaña, formatoFecha); 
    }
    
    //Getters
    public String getIdCampaña() { return idCampaña; }
    public String getNombreCampaña() { return nombreCampaña; }
    public String getUbicacion() { return ubicacion; }
    public LocalDate getFechaCampaña() { return fechaCampaña; }
    public int getMetaDonaciones() { return metaDonaciones; }
    
    //Setters
    public void setIdCampaña(String idCampaña){ this.idCampaña = idCampaña; }
    public void setNombreCampaña(String nombreCampaña){ this.nombreCampaña = nombreCampaña; }
    public void setUbicacion(String ubicacion){ this.ubicacion = ubicacion; }
    public void setFechaCampaña(LocalDate fechaCampaña){ this.fechaCampaña = fechaCampaña; }
    public void setMetaDonaciones(int metaDonaciones){ this.metaDonaciones = metaDonaciones; }
    
    //Metodos
    public String obtenerResumen() {
        return this.idCampaña + " | Meta: " + this.metaDonaciones + "ml";
    }

    @Override
    public void validar() throws Exception {
        // Valida que la metaDonaciones sea positiva
        if (this.metaDonaciones <= 0) {
            throw new Exception("Error: La meta de donaciones debe ser mayor a 0 ml.");
        }
        
        // Valida que el nombre no este en blanco
        if (this.nombreCampaña == null || this.nombreCampaña.trim().isEmpty()) {
            throw new Exception("Error: El nombre de la campaña no puede estar vacío.");
        }
        
        // Valida que la ubicacion no este en blanco
        if (this.ubicacion == null || this.ubicacion.trim().isEmpty()) {
            throw new Exception("Error: La campaña debe tener una ubicación asignada.");
        }
        
        // Valida que la fecha no este en blanco
        if (this.fechaCampaña == null) {
            throw new Exception("Error: La campaña debe tener una fecha programada.");
        }
    }
}