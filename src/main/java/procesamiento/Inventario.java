    package procesamiento;

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
     * SE DEBE MOVER A LA VENTANA RESPECTIVA
     * SE DEBE MOVER A LA VENTANA RESPECTIVA
     * SE DEBE MOVER A LA VENTANA RESPECTIVA
     * Genera un reporte formateado para ser visualizado en Ventanas (Swing).
     */
    public String obtenerInventarioString() {
        if (stockSangre.isEmpty()) return "El inventario está vacío.";

        StringBuilder sb = new StringBuilder("--- STOCK DE SANGRE ---\n");
        for (Map.Entry<String, Integer> entrada : stockSangre.entrySet()) {
            sb.append("Tipo: ").append(entrada.getKey())
                    .append(" | Volumen: ").append(entrada.getValue()).append(" ml\n");
        }
        return sb.toString();
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
}