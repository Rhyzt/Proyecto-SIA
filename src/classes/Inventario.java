/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class Inventario {
    private String tipoSangre;
    private float volumenDisponible;
    
    public Inventario(String tipoSangre, float volumenDisponible) {
        this.tipoSangre = tipoSangre;
        this.volumenDisponible = volumenDisponible;
    }

    // Getters
    public String getTipoSangre() { return tipoSangre; }
    public float getVolumenDisponible() { return volumenDisponible; }

    // Setters
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }
    public void setVolumenDisponible(float volumenDisponible) { this.volumenDisponible = volumenDisponible; }
}

