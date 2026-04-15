package entidades;  

import exceptions.FrecuenciaDonacionException;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Donante extends Persona {
    private String tipoSangre;
    private LocalDate fechaUltimaDonacion;
    private String telefono;
    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public Donante(String nombre, String rut, int edad, String sexo, String tipoSangre, String fechaUltimaDonacion, String telefono) {
        super(nombre, rut, edad, sexo);
        this.tipoSangre = tipoSangre;
        this.fechaUltimaDonacion = LocalDate.parse(fechaUltimaDonacion, formatoFecha);
        this.telefono = telefono;
    }
    
    //Getters
    public String getTipoSangre() { return tipoSangre; }
    public LocalDate getFechaUltimaDonacion() { return fechaUltimaDonacion; }
    public String getTelefono() { return telefono; }
    
    //Setters
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }
    public void setFechaUltimaDonacion(LocalDate fechaUltimaDonacion) { this.fechaUltimaDonacion = fechaUltimaDonacion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    //Metodos
    @Override
    /** Obtiene informacion del donante
     * */
    public void obtenerResumen() {
        super.obtenerResumen();
        System.out.println("Tipo de Sangre: " + tipoSangre);
        String fechaFormateada = fechaUltimaDonacion.format(formatoFecha);
        System.out.println("Fecha ultima donacion: " + fechaFormateada);
        System.out.println("Numero Telefonico: " + telefono);        
    }
    /**
     * Revisa si el donante es apto para donar y si no arroja FrecuenciaDonacionException
     * @param fechaCampaña
     * @return Un booleano que indica si el donante es apto para donar (mas de 4 meses desde la ultima donacion y edad entre 18 a 65)
     * @throws FrecuenciaDonacionExceptieon 
     */
    public boolean esAptoParaDonar(LocalDate fechaCampaña) throws FrecuenciaDonacionException { 
        if (fechaUltimaDonacion == null) return true;
        
        if (fechaUltimaDonacion.isAfter(fechaCampaña.minusMonths(4))) {
            throw new FrecuenciaDonacionException("No han pasado 4 meses desde la última donación.");
        }
        return true;
    }
    
    
}
