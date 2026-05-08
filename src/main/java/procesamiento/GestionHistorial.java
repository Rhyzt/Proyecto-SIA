package procesamiento;

import exceptions.FrecuenciaDonacionException;
import entidades.InfoDonacion;
import entidades.Extraccion;
import entidades.Donante;
import entidades.Campaña;
import entidades.CampañaEnfocada;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    //Getters copias
    public List<Campaña> obtenerCopiaCampañas() {
        return new ArrayList<>(this.campañas);
    }
    
    public Map<String, Donante> obtenerCopiaVoluntarios() {
        return new HashMap<>(this.voluntarios);
    }
    
    public Map<String, List<Extraccion>> obtenerCopiaHistorial() {
        return new HashMap<>(this.historial);
    }
    
    public Map<String, Integer> obtenerCopiaInventario() {
        return inv.obtenerCopiaStock();
    }

    //Metodos Campaña
    /**
     * Agrega una campaña a la lista y al mapa.
     * @param c
     * @return Un booleano que es true si se pudo agregar esta campaña y false si no (ya existia en el sistema)
     */
    public boolean agregarCampaña(Campaña c) {
        // Validacion
        try {
            c.validar();
        } catch (Exception e) {
            System.err.println("Ingreso rechazado. Error en Campaña: " + e.getMessage());
            return false;
        }
        
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
    
    public List<String> obtenerListaCampañas() {
        List<String> listaResumenes = new ArrayList<>();
        for (Campaña c : campañas) {
            listaResumenes.add(c.obtenerResumen());
        }
        return listaResumenes;
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
        // Validacion
        try {
            e.validar();
        } catch (Exception ex) {
            System.err.println("Ingreso rechazado. Error en Extracción: " + ex.getMessage());
            return false;
        }
        
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
    public List<Extraccion> getListaExtracciones(String idCampaña) {
    List<Extraccion> lista = historial.get(idCampaña);
    
    if (lista == null)
        return null;
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
                            e.getVoluntario(),
                            e.getFechaExtraccion(),
                            e.getVolumenExtraido(),
                            e.getSeSintioMal(),
                            c.getIdCampaña()
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
     * @return Un objeto Extraccion con la informacion de la extraccion o null si no se encontro
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
    
    
    
    
    
    
    
    //Metodos Donante
    /**
     * Agrega un donante al mapa de voluntarios
     * @param d
     * @return Un booleano que es true si se pudo agregar al donante y false si no (ya se encontraba en el mapa)
     */
    public boolean agregarDonante(Donante d) {
        // Validacion del donante
        try {
            d.validar(); 
        } catch (Exception e) {
            System.err.println("Ingreso rechazado. Error en Donante: " + e.getMessage());
            return false; // Rechazamos el ingreso al sistema
        }
        // Se revisa si ya se encuentra 
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
                        System.out.println(ex.getMessage());
                    // Se ignora, dono recientemente por lo que no es apto
                }
            }
        }
        return aptos;
    }
    
    /**
     * Exporta los datos de los donantes aptos a un archivo .txt
     * @param aptos Lista con donantes aptos
     * @param nombreArchivo nombre del archivo que se va a exportar
     * @return Un booleano que indica si se pudo exportar correctamente el archivo o no
     */
    public boolean exportarListaAptos(List<Donante> aptos, String nombreArchivo) {
        if (aptos.isEmpty()) return false; 
        
        // Asegurar que exista la carpeta
        File carpeta = new File("exports");
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // Crea la carpeta si no existe
        }
        
        //Convertir a .txt
        if (!nombreArchivo.toLowerCase().endsWith(".txt"))
            nombreArchivo += ".txt";
        
        // Definir la ruta final del archivo
        File archivoDestino = new File(carpeta, nombreArchivo);

        try (PrintWriter escritor = new PrintWriter(archivoDestino)) { 
            escritor.println("=== LISTA DE CONTACTO PARA EMERGENCIA ===");
            escritor.println("Tipo de Sangre Requerido: " + aptos.get(0).getTipoSangre());
            escritor.println("Fecha de generación: " + LocalDate.now().format(formatoFecha));
            escritor.println("-------------------------------------------");

            for (Donante d : aptos) { //Escribe la informacion de cada donante apto
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
    
    public List<String> obtenerListaVoluntarios() {
        List<String> listaResumenes = new ArrayList<>();
        for (Donante d : voluntarios.values()) {
            listaResumenes.add(d.obtenerResumenReducido());
        }
        return listaResumenes;
    }
    
    
    
    
    
    //Edicion
    /**
     * Actualiza un donante que se encuentra con el rut a los datos que se le pasen (pueden ser los mismos de antes)
     * @param rut String para buscar al donante objetivo
     * @return Un booleano que indica si se encontro al donante y pudo actualizar sus datos
     */
    public boolean actualizarDonante(String rut, String nombreNuevo, int edadNuevo, String sexoNuevo, String tipoSangreNuevo, String numeroNuevo) {
        Donante d = buscarDonante(rut);
        if (d != null) {
            d.setNombre(nombreNuevo);
            d.setEdad(edadNuevo);
            d.setSexo(sexoNuevo);
            d.setTelefono(numeroNuevo);
            if (!tipoSangreNuevo.equalsIgnoreCase(d.getTipoSangre())) {
                /*Se deberia restar el stock de todas las extracciones que existan
                que esten asociadas al donante, y luego volver a sumarlas todas al inventario
                */
                actualizarTipoSangre(rut, tipoSangreNuevo);
                d.setTipoSangre(tipoSangreNuevo);
            }
            return true;
        }
        return false;
    }
    
    public void actualizarTipoSangre(String rut, String tipoSangreNuevo) {
        List<InfoDonacion> listaE = buscarExtraccion(rut);
        int volumenTotal = 0;
        for (InfoDonacion inf : listaE) {
            volumenTotal += inf.getVolumenExtraido();
        }
        inv.restarStock(buscarDonante(rut).getTipoSangre(), volumenTotal); //Restado del inventario antiguo
        //Se añade al stock correspondiente
        inv.registrarIngreso(tipoSangreNuevo, volumenTotal);
    }
    
    /**
     * Actualiza una campaña a los datos nuevos que se le pasen
     * @param idOriginal id original de la campaña a editar (si es que se le edita el nombre o fecha)
     * @param nombreCampaña String al que se cambiara el nombreCampaña
     * @param ubicacion String al que se cambiara la ubicacion
     * @param fechaCampaña String de la fecha en formato "DD/MM/AAAA" a cambiar
     * @param metaDonaciones Int de la metaDonaciones a cambiar
     * @return Un boolean que indica si se encontraba la campaña buscada
     * y se pudo cambiar su informacion exitosamente.
     */
    public boolean actualizarCampaña(String idOriginal, String nombreCampaña, String ubicacion, String fechaCampaña, int metaDonaciones) {
        Campaña c = buscarCampaña(idOriginal);
        if (c == null) return false;
        
        try {
            c.setUbicacion(ubicacion);
            c.setMetaDonaciones(metaDonaciones);

            String idNuevo = nombreCampaña.trim() + "_" + fechaCampaña;
            if (!idNuevo.equals(idOriginal)) {
                c.setIdCampaña(idNuevo);
                c.setNombreCampaña(nombreCampaña);
                LocalDate fechaFormateada = LocalDate.parse(fechaCampaña, formatoFecha);
                c.setFechaCampaña(fechaFormateada);
                List<Extraccion> respaldo = historial.remove(idOriginal);
                historial.put(idNuevo, respaldo);
            }
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            // Error al parsear la fecha
            return false;
        }
    }
    
    
    public boolean actualizarExtraccion(String rutDonanteOriginal, String idCampaña,
            String rutDonanteNuevo, String fechaNueva,
            int volumenNuevo, boolean seSintioMalNuevo) {
        Extraccion e = buscarExtraccion(rutDonanteOriginal, idCampaña);
        Donante dNuevo = buscarDonante(rutDonanteNuevo);
        if (e == null  || dNuevo == null) return false;
        
        
        try {
            LocalDate fechaFormateada = LocalDate.parse(fechaNueva, formatoFecha);
            inv.restarStock(e);
            
            e.setFechaExtraccion(fechaFormateada);
            e.setSeSintioMal(seSintioMalNuevo);
            e.setVolumenExtraido(volumenNuevo);
            e.setVoluntario(dNuevo);
            inv.registrarIngreso(e);
            return true;
        } catch (java.time.format.DateTimeParseException ex) {
            // Error al parsear la fecha
            return false;
        }
    }

    
    public CampañaEnfocada crearCampañaEnfocada(String nombreCampaña, String ubicacion, String fechaCampaña, int metaDonaciones, String grupoObjetivo, float porcentajeMeta) {
        CampañaEnfocada nueva = new CampañaEnfocada(
            nombreCampaña,
            ubicacion,
            fechaCampaña, 
            metaDonaciones,
            grupoObjetivo, 
            porcentajeMeta
        );
        
        //Validacion
        try {
            nueva.validar();
        } catch (Exception e) {
            System.err.println("Creación rechazada. Error en Campaña Enfocada: " + e.getMessage());
            return null;
        }
        
        if(historial.containsKey(nueva.getIdCampaña())) return null;
        
        historial.put(nueva.getIdCampaña(), new ArrayList<>());
        campañas.add(nueva);
        return nueva;
    }


//Metodos varios
    public List<String> obtenerResumenInventario() {
        return inv.obtenerEstadoInventario(); 
    }
    
    public void agregarStockManual(String tipo, int cantidad) {
        inv.registrarIngreso(tipo, cantidad);
    }
    
    /**
     * Puente para restar stock. Retorna true si fue exitoso, false si el tipo no existe.
     */
    public boolean restarStockManual(String tipo, int cantidad) {
        return inv.restarStock(tipo, cantidad);
    }
    
    public void cargarDatosInventario(Map<String, Integer> datos) {
        inv.cargarDatos(datos);
    }
}