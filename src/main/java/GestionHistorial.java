
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GestionHistorial {

    // Un Mapa que guarde las extracciones por campaña por idCampaña como key con el valor una lista de extracciones de la misma
    private Map<String, List<Extraccion>> historial;

    // ArrayList necesario para poder realizar operaciones sobre las campañas y Mapa para los donantes
    private List<Campaña> campañas;
    private Map<String, Donante> voluntarios;
    private Inventario inv;

    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public GestionHistorial() {
        historial = new HashMap<>();
        campañas = new ArrayList<>();
        voluntarios = new HashMap<>();
        inv = new Inventario();
    }

    //Metodos
    /** Agrega una campaña a la lista y al mapa, luego retorna true.
     si encontro una campaña con el mismo id retorna false*/
    public boolean agregarCampaña(Campaña c) {
        if (historial.containsKey(c.getIdCampaña())) { // Se revisa si existia la campaña
            return false;
        }
        // Si no, se agrega a la lista y al mapa
        campañas.add(c);
        historial.put(c.getIdCampaña(), new ArrayList<>());
        return true;
    }

    /** Entrega informacion de todas las campañas presentes en la lista */
    public void listarCampañas() {
        System.out.println("LISTADO DE CAMPAÑAS EN EL SISTEMA");
        for (Campaña c : campañas) {
            System.out.println("Nombre: " + c.getNombreCampaña() + " | Ubicacion: " + c.getUbicacion());
            String fecha = c.getFechaCampaña().format(formatoFecha);
            float litros = c.getMetaDonaciones() / 1000f;
            System.out.println("Fecha: " + fecha + " | Meta: " + litros + "L");
            System.out.println("----------------------------------------");
        }
    }

    /** Busca campañas por el id */
    public Campaña buscarCampaña(String id) {
        for (Campaña c : campañas) {
            if (c.getIdCampaña().equals(id)) {
                return (Campaña) c;
            }
        }
        return null;
    }

    /** Elimina una campaña del sistema, junto a las extracciones relacionadas a esta y refleja 
    los cambios en el inventario retornando true, si no pudo encontrar el tipo de Sangre retorna false. */
    public boolean eliminarCampaña(String id) {
        // Se obtiene la lista de extracciones asociadas a la campaña a borrar a la vez que se elimina del mapa
        List<Extraccion> extraccionesABorrar = historial.remove(id); 
        
        if (extraccionesABorrar != null) {
            // Se obtiene cada extraccion y se va restando el tipo de sangre que corresponda
            for (Extraccion e : extraccionesABorrar) { 
                String tipo = e.getVoluntario().getTipoSangre();
                int volumen = e.getVolumenExtraido();

                inv.restarStock(tipo, volumen);
            }
        }
        return campañas.removeIf(c -> c.getIdCampaña().equals(id)); // Borra la campaña de la lista retorna true si existia y false si no
    }
    
    /** Se registra una extraccion, asociandola con la campaña
     y añadiendo lo que corresponda al inventario.
     Ademas actualiza la fecha de ultimo donacion del voluntario*/
    public boolean registrarExtraccion(String idCampaña, Extraccion e) {
        // Se asocia la extraccion con la campaña en el mapa
        if (!historial.containsKey(idCampaña)) return false;
            historial.get(idCampaña).add(e); // Busca la Campaña en el mapa y añade la Extraccion a la ArrayList
            inv.registrarIngreso(e);
        
        // Actualizar al donante
        e.getVoluntario().setFechaUltimaDonacion(e.getFechaExtraccion());
        agregarDonante(e.getVoluntario());
        }

    public List<Extraccion> obtenerExtracciones(String idCampaña) {
        return historial.getOrDefault(idCampaña, new ArrayList<>());
    }
    
    public boolean agregarDonante(Donante d) {
        if (voluntarios.containsKey(d.getRut()))
            return false;
        voluntarios.put(d.getRut(), d);
        return true;
    }
    
    public Donante buscarDonante(String rut) {
        return voluntarios.get(rut); //retorna null si no existe el donante
    }

    public List<Donante> filtroTipoAntiguedad(String tipoSangre) {
        List<Donante> aptos = new ArrayList<>();
        
        for (Donante d: voluntarios.values()) { // Se itera la lista de donantes
            if (d.getTipoSangre().equalsIgnoreCase(tipoSangre)) {
                try {
                   if (d.esAptoParaDonar(LocalDate.now()))
                       aptos.add(d);
                } catch (FrecuenciaDonacionException ex) {
                    // Se ignora, dono recientemente por lo que no es apto
                }
            }
        }
        
        return aptos;
    }
    
}
