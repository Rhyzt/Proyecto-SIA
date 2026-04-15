import java.util.Map;
import java.util.TreeMap;

public class Inventario {
    private Map<String, Integer> stockSangre;

    public Inventario() {
        // TreeMap asegura que los tipos (A+, O-, etc.) aparezcan ordenados alfabéticamente (SIA-4)
        stockSangre = new TreeMap<>();
    }

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
     * SOBRECARGA 1 (SIA-5): Printea el inventario completo.
     */
    public void mostrarInventario() {
        System.out.println("\n--- ESTADO GENERAL DEL INVENTARIO ---");
        if (!stockSangre.isEmpty()) {
            for (Map.Entry<String, Integer> entrada : stockSangre.entrySet()) {
                System.out.println("Tipo: " + entrada.getKey() + " | Volumen: " + entrada.getValue() + " ml");
            }
        } else {
            System.out.println("El inventario está vacío.");
        }
    }

    /**
     * SOBRECARGA 2 (SIA-5): Seguimiento de disponibilidad crítica.
     * Busca y muestra solo los tipos de sangre que están bajo el límite especificado.
     * @param limiteCritico Volumen mínimo de seguridad en ml.
     */
    public void mostrarInventario(int limiteCritico) {
        System.out.println("\n--- ALERTAS DE RESERVAS CRÍTICAS (Bajo " + limiteCritico + "ml) ---");
        boolean hayAlertas = false;

        for (Map.Entry<String, Integer> entrada : stockSangre.entrySet()) {
            if (entrada.getValue() < limiteCritico) {
                System.out.println("¡ALERTA! Tipo " + entrada.getKey() + " requiere reposición inmediata. Stock: " + entrada.getValue() + "ml");
                hayAlertas = true;
            }
        }

        if (!hayAlertas) {
            System.out.println("No se detectaron reservas bajo el nivel crítico de seguridad.");
        }
    }

    /**
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