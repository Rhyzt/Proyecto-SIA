/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class Donante extends Persona {
    private String tipoSangre;
    private String fechaDonacion;
    private String fechaUltimaDonacion;
    private String telefono;
    
    public Donante(String nombre, String rut, int edad, String sexo, String numeroTelefonico, String tipoSangre) {
        super(nombre, rut, edad, sexo);
        this.tipoSangre = tipoSangre;
        this.fechaDonacion = fechaDonacion;
        this.fechaUltimaDonacion = fechaUltimaDonacion;
        this.telefono = telefono;
    }
    
    //Getters
    public String getTipoSangre() { return tipoSangre; }
    public String getFechaDonacion() { return fechaDonacion; }
    public String getFechaUltimaDonacion() { return fechaUltimaDonacion; }
    public String getTelefono() { return telefono; }
    
    //Setters
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }
    public void setFechaDonacion(String fechaDonacion) { this.fechaDonacion = fechaDonacion; }
    public void setFechaUltimaDonacion(String fechaUltimaDonacion) { this.fechaUltimaDonacion = fechaUltimaDonacion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
