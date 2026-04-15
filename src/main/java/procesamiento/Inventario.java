    package procesamiento;

import entidades.Donante;
import entidades.Extraccion;
import java.util.Map;
import java.util.TreeMap;

public class Inventario {
    private Map<String, Integer> stockSangre;

    public Inventario() {
        // Para que los tipos esten ordenados alfabeticamente, se usaran en mayuscula (ej. AB+)
        stockSangre = new TreeMap<>();
    }
    
    //Getters
    public Map<String, Integer> getStockSangre() { return stockSangre; }
    
    //Setters
    public void setStockSangre(Map<String, Integer> stockSangre) { this.stockSangre = stockSangre; }
    
    
    

    /**
     * Registra el ingreso de sangre al stock tras una extracción.
     */
    public void registrarIngreso(Extraccion donacion) {
        int volumen = donacion.getVolumenExtraido();
        String tipoSangre = donacion.getVoluntario().getTipoSangre().toUpperCase();

        int actual = stockSangre.getOrDefault(tipoSangre, 0);
        stockSangre.put(tipoSangre, actual + volumen);
    }
    
    /**
     * Registra un ingreso usando solo el volumen de la extraccion y el tipo de sangre
     * @param tipoDeseado
     * @param volumen 
     */
    public void registrarIngreso(String tipoDeseado, int volumen) {
        String tipoForm = tipoDeseado.toUpperCase().trim();
        if (stockSangre.containsKey(tipoForm)) {
            int volumenActual = stockSangre.get(tipoForm);
            // Evita que el stock sea negativo
            stockSangre.put(tipoForm, Math.max(0, volumenActual + volumen));
        }
    }

    /**
     * Resta volumen del stock (usado al eliminar campañas o realizar traslados).
     */
    public boolean restarStock(String tipoDeseado, int volumen) {
        String tipoForm = tipoDeseado.toUpperCase().trim();
        if (stockSangre.containsKey(tipoForm)) {
            int volumenActual = stockSangre.get(tipoForm);
            // Evita que el stock sea negativo
            stockSangre.put(tipoForm, Math.max(0, volumenActual - volumen));
            return true;
        }
        return false;
    }
    
    /**
     * Resta el stock en el inventario de una extraccion
     * @param e Un objeto del tipo Extraccion
     * @return Un booleano que indica si existia en el sistema el tipo de sangre y se resto
     */
    public boolean restarStock(Extraccion e) {
        Donante d = e.getVoluntario();
        if (stockSangre.containsKey(d.getTipoSangre())) {
            int volumenActual = stockSangre.get(d.getTipoSangre());
            // Evita que el stock sea negativo
            stockSangre.put(d.getTipoSangre(), Math.max(0, volumenActual - e.getVolumenExtraido()));
            return true;
        }
        return false;
    }
}