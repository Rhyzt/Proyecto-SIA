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
    public void setFechaUltimaDonacion(String fechaUltimaDonacion) {
        this.fechaUltimaDonacion = LocalDate.parse(fechaUltimaDonacion, formatoFecha);
    }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    //Metodos
    @Override
    public void obtenerResumen() {
        super.obtenerResumen();
        System.out.println("Tipo de Sangre: " + tipoSangre);
        String fechaFormateada = fechaUltimaDonacion.format(formatoFecha);
        System.out.println("Fecha ultima donacion: " + fechaFormateada);
        System.out.println("Numero Telefonico: " + telefono);        
    }
    
    public boolean esAptoParaDonar(LocalDate fechaCampaña) { // Comprobar que no haya donado en los ultimos 4 meses
        Period periodo = Period.between(fechaCampaña, fechaUltimaDonacion);
        return (!(periodo.getYears() == 0 && periodo.getMonths() < 4));
    }
    
    
}
