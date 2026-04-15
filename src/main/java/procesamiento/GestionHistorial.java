package procesamiento;

import exceptions.FrecuenciaDonacionException;
import entidades.InfoDonacion;
import entidades.Extraccion;
import entidades.Donante;
import entidades.Campaña;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.PrintWriter;
import java.io.IOException;

public class GestionHistorial {

    // Un Mapa que guarde las extracciones por campaña por idCampaña como key con el valor una lista de extracciones de la misma
    private Map<String, List<Extraccion>> historial;

    // ArrayList necesario para poder realizar operaciones sobre las campañas y Mapa para los donantes
    private List<Campaña> campañas;
    private Map<String, Donante> voluntarios;
    private Inventario inv;
    
    //Constante usada para formatear 
    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public GestionHistorial() {
        historial = new HashMap<>();
        campañas = new ArrayList<>();
        voluntarios = new HashMap<>();
        inv = new Inventario();
    }
    
    //Getters
    public List<Campaña> getCampañas() {
        return campañas;
    }

    public Map<String, Donante> getVoluntarios() {
        return voluntarios;
    }

    public Map<String, List<Extraccion>> getHistorial() {
        return historial;
    }
    public Inventario getInv() {
        return inv;
    }
    
    
    //Setters
    public void setCampañas(List<Campaña> campañas) { this.campañas = campañas; }
    public void setVoluntarios(Map<String, Donante> voluntarios) { this.voluntarios = voluntarios; }
    public void setHistorial(Map<String, List<Extraccion>> historial) { this.historial = historial; }
    public void setInv(Inventario inv) { this.inv = inv; }
    
    
    
    
    
    //Metodos Campaña
    /**
     * Agrega una campaña a la lista y al mapa.
     * @param c
     * @return Un booleano que es true si se pudo agregar esta campaña y false si no (ya existia en el sistema)
     */
    public boolean agregarCampaña(Campaña c) {
        if (historial.containsKey(c.getIdCampaña())) { // Se revisa si existia la campaña
            return false;
        }
        // Si no, se agrega a la lista y al mapa
        campañas.add(c);
        historial.put(c.getIdCampaña(), new ArrayList<>());
        return true;
    }

    /**
     * Obtiene todas las campañas presentes
     * @return Una Lista con todas las campañas que existen en memoria
     */
    public List<Campaña> getListaCampañas() {
        return new ArrayList<>(campañas); // Retorna una copia de la lista
    }

    /**
     * Busca campañas por el id de esta
     * @param idCampaña
     * @return Un Objeto Campaña correspondiente al id entregado
     */
    public Campaña buscarCampaña(String idCampaña) {
        for (Campaña c : campañas) {
            if (c.getIdCampaña().equals(idCampaña)) {
                return (Campaña) c;
            }
        }
        return null;
    }

    /**
     * Elimina una campaña del sistema, junto a las extracciones  
     * relacionadas a esta y refleja los cambios en el inventario.
     * @param idCampaña
     * @return Un booleano que da true si se borro la campaña o false si no (no se encontro la campaña especificada)
     */
    public boolean eliminarCampaña(String idCampaña) {
        // Se obtiene la lista de extracciones asociadas a la campaña a borrar a la vez que se elimina del mapa
        List<Extraccion> extraccionesABorrar = historial.remove(idCampaña); 
        
        if (extraccionesABorrar != null) {
            // Se obtiene cada extraccion y se va restando el tipo de sangre que corresponda
            for (Extraccion e : extraccionesABorrar) { 
                String tipo = e.getVoluntario().getTipoSangre();
                int volumen = e.getVolumenExtraido();

                inv.restarStock(tipo, volumen);
            }
        }
        return campañas.removeIf(c -> c.getIdCampaña().equals(idCampaña)); // Borra la campaña de la lista retorna true si existia y false si no
    }
    
    
    
    
    
    
    //Metodos Extraccion
    /**
     * Se registra una extraccion, asociandola con la campaña
     * y añadiendo lo que corresponda al inventario.
     *  Ademas, actualiza la fecha de ultimo donacion del voluntario
     * @param idCampaña
     * @param e
     * @return Un booleano que indica si la extraccion fue agregada o no (no existia la campaña)
     */
    public boolean registrarExtraccion(String idCampaña, Extraccion e) {
        // Se asocia la extraccion con la campaña en el mapa
        if (!historial.containsKey(idCampaña)) return false;
        
        historial.get(idCampaña).add(e); // Busca la Campaña en el mapa y añade la Extraccion a la ArrayList
        inv.registrarIngreso(e);
        
        // Actualizar al donante
        e.getVoluntario().setFechaUltimaDonacion(e.getFechaExtraccion());
        agregarDonante(e.getVoluntario());
        return true;
    }
    
    /**
     * Obtiene las extracciones de una campaña en especifico
     * @param idCampaña
     * @return Una Lista con extracciones
     */
    public List<Extraccion> getlistaExtracciones(String idCampaña) {
        List<Extraccion>lista = historial.get(idCampaña);
        
        if (lista == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(lista);
    }
    
    /**
     * Busca en el mapa todas las extracciones que pertenezcan al rut dado.
     * @param rut
     * @return Una lista con la informacion de las extracciones y su campaña respectiva.
     */
    public List<InfoDonacion> buscarExtraccion(String rut) {
        List<InfoDonacion> coincidencias = new ArrayList<>();
        
        /* Se recorren todas las campañas para obtener su informacion respectiva,
        buscando las que el rut del donante coincidan con el valor ingresado */
        for (Campaña c : campañas) {
            // Obtenemos la lista de extracciones de cada campaña especifica
            List<Extraccion> listaCampaña = historial.get(c.getIdCampaña()); 

            if (listaCampaña != null) {
                for (Extraccion e : listaCampaña) {
                    //Si se encuentra una extraccion coincidente, se crea un objeto InfoDonacion
                    if (e.getVoluntario().getRut().equalsIgnoreCase(rut)) {
                        coincidencias.add(new InfoDonacion(
                            c.getNombreCampaña(),
                            c.getFechaCampaña(),
                            e.getVolumenExtraido(),
                            e.getSeSintioMal()
                        ));
                    }
                }
            }
        }
        return coincidencias;
    }
    
    /**
     * Busca en el mapa todas las extracciones que pertenezcan al rut dado de la campaña especificada.
     * @param rut
     * @param idCampaña
     * @return 
     */
    public Extraccion buscarExtraccion(String rut, String idCampaña) {
        if(!historial.containsKey(idCampaña)) return null;
        
        for (Extraccion e : historial.get(idCampaña)) {
            if (e.getVoluntario().getRut().equalsIgnoreCase(rut)) {
                return e;
            }
        }
       return null;
    }
    
    /**
     * Borra una extraccion usando el rut y la id de la campaña, retorna
     * @param idCampaña
     * @param rut
     * @return 
     */
    public boolean borrarExtraccion(String idCampaña, String rut) {
        if (!historial.containsKey(idCampaña)) return false;
        
        List<Extraccion> lista = historial.get(idCampaña);
        return lista.removeIf(e -> { // Se itera por cada extraccion de la campaña
            if (e.getVoluntario().getRut().equalsIgnoreCase(rut)) { // Si existe una extraccion con el rut indicado
                inv.restarStock(e.getVoluntario().getTipoSangre(), e.getVolumenExtraido()); // Se actualiza el inventario
                return true;
            }
            return false;
        });
    }
    
    /**
     * Obtiene las extracciones de una campaña especifica
     * @param idCampaña
     * @return Una Lista de Extracciones correspondientes a la campaña indicada, si no existia esta campaña, retorna null
     */
    public List<Extraccion> obtenerExtracciones(String idCampaña) {
        return historial.get(idCampaña);
    }
    
    
    
    
    
    
    
    //Metodos Donante
    /**
     * Agrega un donante al mapa de voluntarios
     * @param d
     * @return Un booleano que es true si se pudo agregar al donante y false si no (ya se encontraba en el mapa)
     */
    public boolean agregarDonante(Donante d) {
        if (voluntarios.containsKey(d.getRut()))
            return false;
        voluntarios.put(d.getRut(), d); 
        return true;
    }
    
    /**
     * Busca un donante en el mapa
     * @param rut
     * @return Un objeto Donante con la informacion de este, si no se encontro el rut especificado retorna null
     */
    public Donante buscarDonante(String rut) {
        return voluntarios.get(rut); //retorna null si no existe el donante
    }
    
    /**
     * Elimina un donante del mapa de voluntarios y todas sus extracciones asociadas en las campañas
     * @param rut
     * @return Un booleano, que indica true si se borro al donante y sus extracciones, y false si no se encontro el Donante
     */
    public boolean eliminarDonante(String rut) {
        Donante d = voluntarios.get(rut);
        if (d == null) return false;
        
        for (List<Extraccion> listaCampaña : historial.values()) {
            listaCampaña.removeIf(e -> {
                if (e.getVoluntario().getRut().equalsIgnoreCase(rut)) {
                    inv.restarStock(d.getTipoSangre(), e.getVolumenExtraido());
                    return true;
                }
                return false;
            });
        }
        voluntarios.remove(rut);
        return true;
    }
    
    /**
     * Filtra el mapa de donantes por el tipo de sangre especificado.
     * @param tipoSangre
     * @return Una Lista de Donantes que poseen el tipo de sangre y ademas no arrojan FrecuenciaDonacionException (son aptos para donar)
     */
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
    
    /**
     * Exporta los datos de los donantes aptos a un archivo de texto
     * @param aptos
     * @param nombreArchivo nombre del archivo que se va a exportar
     * @return Un booleano que indica si se pudo exportar correctamente el archivo o no
     */
    public boolean exportarListaAptos(List<Donante> aptos, String nombreArchivo) {
        if (aptos.isEmpty()) return false; // Se checkea si existen donantes aptos
        
        try (PrintWriter escritor = new PrintWriter(nombreArchivo)) {
            escritor.println("=== LISTA DE CONTACTO PARA EMERGENCIA ===");
            escritor.println("Tipo de Sangre Requerido: " + aptos.get(0).getTipoSangre());
            escritor.println("Fecha de generación: " + LocalDate.now().format(formatoFecha));
            escritor.println("-------------------------------------------");

            for (Donante d : aptos) { // Se formatean los datos para que se vea mejor 
                escritor.printf("Nombre: %-40s | Teléfono: %-16s | RUT: %s%n", 
                              d.getNombre(), d.getTelefono(), d.getRut());
            }

            escritor.println("-------------------------------------------");
            escritor.println("Total de donantes aptos encontrados: " + aptos.size());
            return true;
        } catch (IOException ex) {
            System.out.println("Error al exportar el archivo: " + ex.getMessage());
            return false;
        }
    }
}
