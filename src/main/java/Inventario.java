import java.util.Map;
import java.util.TreeMap;

public class Inventario {
    private Map<String, Float> stockSangre = new TreeMap<>();
    
    public void registrarIngreso(Extraccion donacion, String tipoSangre) {
        float volumen = donacion.getVolumenExtraido();
        float actual = stockSangre.getOrDefault(tipoSangre, 0.0f);
        stockSangre.put(tipoSangre, actual + volumen);
    }

    
}

