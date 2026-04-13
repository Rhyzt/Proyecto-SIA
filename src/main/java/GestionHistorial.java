import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionHistorial {
    // Un Mapa que guarde las extracciones por campaña por idCampaña como key con el valor una lista de extracciones de la misma
    private Map<String, List<Extraccion>> historial; 
    
    // ArrayList necesario para poder realizar operaciones sobre las campañas
    private List<Campaña> campañas;
    
    public GestionHistorial() {
        this.historial = new HashMap<>();
        this.campañas = new ArrayList<>();
    }
    
    
    //Metodos
    public boolean agregarCampaña(Campaña c) {
        campañas.add(c);
        if(historial.containsKey(c.getIdCampaña()))
            return false;
        historial.put(c.getIdCampaña(), new ArrayList<>());
        return true;
    }
    
    //Busca campañas por el id (nombreCampaña_fechaCampaña)
    public Campaña buscarCampaña(String id) { 
        for (Campaña c : campañas) { 
            if(c.getIdCampaña().equals(id))
                return (Campaña) c;
            }
        return null;
    }
    
    public boolean eliminarCampaña(String id) { 
        historial.remove(id);
        return campañas.removeIf(c -> c.getIdCampaña().equals(id));

    }
    
    public void registrarExtraccion(String idCampaña, Extraccion e) {
        if(historial.containsKey(idCampaña))
            historial.get(idCampaña).add(e); // Busca la Campaña en el mapa y añade la Extraccion a la ArrayList
    }
    
    public List<Extraccion> obtenerExtracciones(String idCampaña) {
        return historial.getOrDefault(idCampaña, new ArrayList<>());
    }
    
    public List<Donante> filtroTipoAntiguedad(String tipoSangre, int meses) {
        List <Donante> aptos = new ArrayList<>();
        LocalDate intervalo = LocalDate.now().minusMonths(meses); // Busqueda segun el tiempo deseado desde la ultima extraccion
        
        for (List<Extraccion> lista : historial.values()) { // Itera el historial con todos los Arrays de extracciones
            for (Extraccion e : lista) { // Itera cada Array de Extraccion
                Donante d = e.getVoluntario();
                if(d.getTipoSangre().equalsIgnoreCase(tipoSangre) && e.getFechaExtraccion().isAfter(intervalo)) { // Filtra donantes por tipoSangre y intervalo deseado
                    try {
                        if(d.esAptoParaDonar(LocalDate.now())) {
                            if(!aptos.contains(d))
                                aptos.add(d);
                        }
                    } catch(FrecuenciaDonacionException ex) {
                        System.out.println("Donante " + d.getNombre() + " omitido: " + ex.getMessage());
                    }
                }
            }
        }
        return aptos;
    }
}

