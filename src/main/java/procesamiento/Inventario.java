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
    
    /**
     * Actualiza el inventario segun la donacion entregada
     * @param donacion 
     */
    public void registrarIngreso(Extraccion donacion) {
        int volumen = donacion.getVolumenExtraido();
        String tipoSangre = donacion.getVoluntario().getTipoSangre();
        
        int actual = stockSangre.getOrDefault(tipoSangre, 0);
        stockSangre.put(tipoSangre, actual + volumen);
    }
    
    /**
     * Printea el inventario, mostrando todos los tipos existentes en el inventario y el volumen que hay disponible.
     */
    public void mostrarInventario() {
        if (!stockSangre.isEmpty()) {
            for (Map.Entry<String, Integer> entrada : stockSangre.entrySet()) {
                System.out.println("Tipo: " + entrada.getKey() + " | Volumen: " + entrada.getValue() + " ml");
            }
            return;
        }
        System.out.println("El inventario está vacío.");
    }
    
    /**
     * Printea la informacion del tipo deseado de sangre, si no existe, avisa que este tipo de sangre no existe en el inventario
     * @param tipoDeseado 
     */
    public void mostrarInventario(String tipoDeseado) {
        String tipoForm = tipoDeseado.toUpperCase().trim();
        if (stockSangre.containsKey(tipoForm))
            System.out.println("Tipo " + tipoForm + " tiene " + stockSangre.get(tipoForm) + "ml disponibles.");
        else
            System.out.println("Error: El tipo de sangre '" + tipoForm + "' no se encuentra en el inventario.");
    }
    
    /**
     * Resta el volumen especificado de un tipo de sangre.
     * @param tipoDeseado
     * @param volumen
     * @return Un booleano que da true si se redujo la sangre y false si no (no existia el tipo de sangre en el inventario)
     */
    public boolean restarStock(String tipoDeseado, int volumen) {
        String tipoForm = tipoDeseado.toUpperCase().trim();
        if (stockSangre.containsKey(tipoForm)) { // Verificar si el tipo de sangre existe en el mapa
            int volumenActual = stockSangre.get(tipoForm);
            
            if(volumenActual > volumen) { // Se resta el volumen 
                stockSangre.replace(tipoForm, volumenActual - volumen);
            } else {
                stockSangre.replace(tipoForm, 0); // Si quedaria bajo 0, simplemente se asigna a 0 para evitar valores negativos
            }
            return true;
        }
        return false;
    }
    
}

