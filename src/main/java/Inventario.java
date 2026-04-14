import java.util.Map;
import java.util.TreeMap;

public class Inventario {
    private Map<String, Integer> stockSangre;
    
    public Inventario() {
         stockSangre = new TreeMap<>();
    }
        
    public void registrarIngreso(Extraccion donacion) {
        int volumen = donacion.getVolumenExtraido();
        String tipoSangre = donacion.getVoluntario().getTipoSangre();
        
        int actual = stockSangre.getOrDefault(tipoSangre, 0);
        stockSangre.put(tipoSangre, actual + volumen);
    }
    
    public boolean mostrarInventario() {
        if (!stockSangre.isEmpty()) {
            for (Map.Entry<String, Integer> entrada : stockSangre.entrySet()) {
                System.out.println("Tipo: " + entrada.getKey() + " | Volumen: " + entrada.getValue() + " ml");
            }
        }
        return false;
    }
    
    /* Resta el volumen especificado de un tipo de sangre, retorna true si lo encontro y resto el volumen,
    retorna falso si el tipo de sangre no se encontraba en el inventario */
    public boolean restarStock(String tipo, int volumen) { 
        if (stockSangre.containsKey(tipo)) { // Verificar si el tipo de sangre existe en el mapa
            int volumenActual = (int) stockSangre.get(tipo);
            
            if(volumenActual > volumen) { // Se resta el volumen 
                stockSangre.replace(tipo, volumenActual - volumen);
            } else {
                stockSangre.replace(tipo, 0); // Si quedaria bajo 0, simplemente se asigna a 0 para evitar valores negativos
            }
            return true;
        }
        return false;
    }
    
}

