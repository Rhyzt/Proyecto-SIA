package vista;

import exceptions.EdadNoValidaException;
import entidades.Campaña;
import entidades.CampañaEnfocada;
import entidades.Donante;
import entidades.Extraccion;
import entidades.InfoDonacion;
import procesamiento.GestionHistorial;
import procesamiento.Validadores;
import java.util.Scanner;
import java.util.List;

public class MenuConsola {
    private GestionHistorial sistema;
    private Scanner leer;
    

    public MenuConsola(GestionHistorial sistema) {
        this.sistema = sistema;
        this.leer = new Scanner(System.in);
    }

    public void desplegar() {
        int op = 0;
        do {
            System.out.println("\n--- SISTEMA GESTIÓN DE DONACIONES ---");
            System.out.println("1. Administrar Campañas");
            System.out.println("2. Administrar Donantes");
            System.out.println("3. Administrar Extracciones");
            System.out.println("4. Administrar Inventario");
            System.out.println("5. Llamado de Emergencia"); // Nueva opción visible
            System.out.println("6. Salir y Guardar");
            System.out.println("Seleccione una opción: ");

            try {
                op = Integer.parseInt(leer.nextLine());
                switch(op) {
                    case 1: administrarCampañas(); break;
                    case 2: administrarDonantes(); break;
                    case 3: administrarExtracciones(); break;
                    case 4: administrarInventario(); break;
                    case 5: llamadoEmergencia(); break;
                    case 6: System.out.println("Guardando datos..."); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (op != 6); 
    }
    
    //Metodos submenus
    
    private void administrarCampañas() {
        int subOp = 0;
        do {
            System.out.println("\n--- ADMINISTRAR CAMPAÑAS ---");
            System.out.println("1. Crear Nueva Campaña");
            System.out.println("2. Listar Todas las Campañas");
            System.out.println("3. Buscar Campaña (ID)");
            System.out.println("4. Editar Datos de Campaña");
            System.out.println("5. Eliminar Campaña");
            System.out.println("6. Volver");
            System.out.println("Seleccione una opción: ");
            
            try {
                subOp = Integer.parseInt(leer.nextLine());
                switch(subOp) {
                    case 1: registrarCampana(); break;
                    case 2: listarCampanas(); break;
                    case 3: buscarCampana(); break;
                    case 4: editarCampana(); break;
                    case 5: eliminarCampana(); break;
                    case 6: break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) { 
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (subOp != 6);
    }
    
    private void administrarDonantes() {
        int subOp = 0; 
        do {
            System.out.println("\n--- ADMINISTRAR DONANTES ---");
            System.out.println("1. Registrar Nuevo Donante");
            System.out.println("2. Listar Todos los Donantes");
            System.out.println("3. Editar Datos de Donante");
            System.out.println("4. Buscar Donante (RUT)");
            System.out.println("5. Eliminar Donante");
            System.out.println("6. Volver");
            System.out.println("Seleccione una opción: ");

            try {
                subOp = Integer.parseInt(leer.nextLine()); 
                switch(subOp) {
                    case 1: registrarDonante(); break;
                    case 2: listarDonantes(); break;
                    case 3: editarDonante(); break;
                    case 4: buscarDonante(); break;
                    case 5: eliminarDonante(); break;
                    case 6: break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (subOp != 6);
    }
    
    private void administrarExtracciones() {
        int subOp = 0;
        do {
            System.out.println("\n--- ADMINISTRAR EXTRACCIONES ---");
            System.out.println("1. Registrar Nueva Extraccion");
            System.out.println("2. Listar Extraccion de una Campaña");
            System.out.println("3. Mostrar Historial de Donante (RUT + ID)");
            System.out.println("4. Editar Datos de Extraccion");
            System.out.println("5. Eliminar Extraccion");
            System.out.println("6. Volver");
            System.out.println("Seleccione una opción: ");
            
            try {
                subOp = Integer.parseInt(leer.nextLine());
                switch(subOp) {
                    case 1: registrarExtraccion(); break;
                    case 2: listarExtraccionesPorCampaña(); break;
                    case 3: historialDonante(); break;
                    case 4: editarExtraccion(); break;
                    case 5: eliminarExtraccion(); break;
                    case 6: break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (subOp != 6);
    }
    
    private void administrarInventario() {
        int subOp = 0;
        do {
            System.out.println("\n--- ADMINISTRAR INVENTARIO ---");
            System.out.println("1. Mostrar Inventario General");
            System.out.println("2. Agregar Stock Manual");
            System.out.println("3. Restar Stock Manual");
            System.out.println("4. Volver");
            System.out.println("Seleccione una opción: ");
            
            try {
                subOp = Integer.parseInt(leer.nextLine());
                switch(subOp) {
                    case 1: mostrarInventario(); break;
                    case 2: agregarStockManual(); break;
                    case 3: restarStockManual(); break;
                    case 4: break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (subOp != 4);
    }
    
    // --- MÉTODOS DE REGISTRO ---
    private void registrarDonante() {
        try {
            System.out.println("Nombre: "); 
            String n = leer.nextLine();

            System.out.println("RUT: "); 
            String r = leer.nextLine();

            System.out.println("Edad: ");
            int e = Integer.parseInt(leer.nextLine());
            
            Validadores.validarEdad(e); 

            System.out.println("Tipo Sangre: "); 
            String t = leer.nextLine();

            System.out.println("Teléfono: "); 
            String tel = leer.nextLine();

            Donante d = new Donante(n, r, e, "M", t, "01/01/2000", tel);

            if (sistema.agregarDonante(d)) {
                System.out.println("Donante registrado.");
            }

        } catch (EdadNoValidaException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Debe ingresar un número válido para la edad.");
        }
    }

    private void registrarCampana() {
        System.out.println("Nombre: "); String n = leer.nextLine();
        System.out.println("Ubicación: "); String u = leer.nextLine();
        System.out.println("Fecha: "); String f = leer.nextLine();
        System.out.println("Meta (ml): "); int m = Integer.parseInt(leer.nextLine());
        if (sistema.agregarCampaña(new Campaña(n, u, f, m))) System.out.println("Campaña creada.");
    }
    
    private void registrarExtraccion() {
        System.out.println("ID Campaña: "); String id = leer.nextLine();
        System.out.println("RUT Donante: "); String rut = leer.nextLine();
        Campaña c = sistema.buscarCampaña(id);
        Donante d = sistema.buscarDonante(rut);
        if (c != null && d != null) {
           try {
                // Se revisa si es apto para donar primero, si no, arroja FrecuenciaDonacionException
                d.esAptoParaDonar(c.getFechaCampaña());
                
                // Si es apto, se piden los demas datos
                System.out.println("Volumen (ml): ");
                int v = Integer.parseInt(leer.nextLine());
                
                sistema.registrarExtraccion(id, new Extraccion(d, c.getFechaCampaña(), v, false));
                System.out.println("Exito: Extracción registrada correctamente.");
                
            } catch (exceptions.FrecuenciaDonacionException ex) {
                // Si la excepcion ocurre, se muestra en consola
                System.out.println("Donación rechazada: " + ex.getMessage());
                
            } catch (NumberFormatException ex) {
                System.out.println("Error: El volumen debe ser un numero entero.");
            } catch (Exception ex) {
                System.out.println("Error de validacion: " + ex.getMessage());
            }
        } else {
            System.out.println("Error: La campaña o el donante no existen en el sistema.");
        }
    }
    
    private void agregarStockManual() {
        System.out.println("\n--- AGREGAR STOCK MANUAL ---");
        try {
            System.out.println("Ingrese Tipo de Sangre (ej: O+, A-): ");
            String tipoSangre = leer.nextLine().trim().toUpperCase(); 
            
            System.out.println("Ingrese Volumen a agregar (en ml): ");
            int cantidad = Integer.parseInt(leer.nextLine());
            
            sistema.agregarStockManual(tipoSangre, cantidad); 
            System.out.println("Stock de sangre " + tipoSangre + " actualizado correctamente");
        } catch (Exception e) {
            System.out.println("Error: Asegúrese de ingresar un número entero para el volumen.");
        }
    }
    
    // --- MÉTODOS DE BÚSQUEDA ---
    private void buscarDonante() {
        System.out.println("RUT: ");
        Donante d = sistema.buscarDonante(leer.nextLine());
        if (d != null) d.obtenerResumen();
        else System.out.println("No encontrado.");
    }

    private void buscarCampana() {
        System.out.println("Nombre de Campaña: ");
        String n = leer.nextLine();
        
        System.out.println("Fecha Campaña: ");
        String f = leer.nextLine();
        
        Campaña c = sistema.buscarCampaña(n + "_" + f);
        if (c != null) System.out.println("Nombre: " + c.getNombreCampaña() + " | Meta: " + c.getMetaDonaciones());
        else System.out.println("No encontrada.");
    }
    
    private void crearCampanaEnfocada(String grupoObjetivo) {
        System.out.println("--- CONFIGURACION DE CAMPAÑA DE EMERGENCIA ---");
        
        //Parametros por defecto
        String nombreCampaña = "(" + grupoObjetivo + ")Emergencia";
        String sede = "SinSede";
        int meta = 10000; // 10 litros

        // Parametros manuales
        System.out.println("Ingrese la fecha de la campaña (dd/mm/yyyy): ");
        String fecha = leer.nextLine();
        

        System.out.println("Ingrese el porcentaje objetivo del grupo (0 a 100): ");
        float porcentajeMeta = Float.parseFloat(leer.nextLine()) / 100f;
        
        CampañaEnfocada nueva = sistema.crearCampañaEnfocada(nombreCampaña, sede, fecha, meta, grupoObjetivo, porcentajeMeta);
        if (nueva != null)
            System.out.println("Exito: Se ha creado la campaña con ID: " + nueva.getIdCampaña());
        else 
            System.out.println("Error: Ya existe una campaña con ese ID automatico.");
    }
    
    // --- METODOS DE EDICIÓN ---   
    private void editarDonante() {
        System.out.println("RUT del donante a editar: ");
        String r = leer.nextLine();
        
        Donante d = sistema.buscarDonante(r);

        if (d != null) {
            System.out.println("--- Presione Enter para mantener el valor actual ---");

            // Nombre
            System.out.println("Nuevo Nombre [" + d.getNombre() + "]: ");
            String n = leer.nextLine();
            if (n.isEmpty()) n = d.getNombre();

            // Edad
            System.out.println("Nueva Edad [" + d.getEdad() + "]: ");
            String eStr = leer.nextLine();
            int e = eStr.isEmpty() ? d.getEdad() : Integer.parseInt(eStr);

            // Sexo
            System.out.println("Nuevo Sexo [" + d.getSexo() + "]: ");
            String s = leer.nextLine();
            if (s.isEmpty()) s = d.getSexo();

            // Tipo Sangre
            System.out.println("Nuevo Tipo de Sangre [" + d.getTipoSangre() + "]: ");
            String ts = leer.nextLine().toUpperCase();
            if (ts.isEmpty()) ts = d.getTipoSangre();

            // Telefono
            System.out.println("Nuevo Telefono [" + d.getTelefono() + "]: ");
            String tel = leer.nextLine();
            if (tel.isEmpty()) tel = d.getTelefono();

            if (sistema.actualizarDonante(r, n, e, s, ts, tel)) {
                System.out.println("Datos del donante actualizados.");
            } else {
                System.out.println("Ocurrio un error al intentan actualizar los datos.");
            }
        } else {
            System.out.println("Donante no encontrado.");
        }
    }

    private void editarCampana() {
        System.out.println("Ingrese ID de la Campaña a editar: ");
        String id = leer.nextLine();
        Campaña c = sistema.buscarCampaña(id);

        if (c != null) {
            System.out.println("--- Presione Enter para mantener el valor actual ---");

            // Nombre
            System.out.println("Nuevo Nombre [" + c.getNombreCampaña() + "]: ");
            String n = leer.nextLine();
            if (n.isEmpty()) n = c.getNombreCampaña();

            // Ubicación
            System.out.println("Nueva Ubicación [" + c.getUbicacion() + "]: ");
            String u = leer.nextLine();
            if (u.isEmpty()) u = c.getUbicacion();

            // Fecha
            String fechaActual = c.getFechaCampaña().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("Nueva Fecha [" + fechaActual + "]: ");
            String f = leer.nextLine();
            if (f.isEmpty()) f = fechaActual;

            // Meta
            System.out.println("Nueva Meta [" + c.getMetaDonaciones() + " ml]: ");
            String mStr = leer.nextLine();
            int m = mStr.isEmpty() ? c.getMetaDonaciones() : Integer.parseInt(mStr);

            if (sistema.actualizarCampaña(id, n, u, f, m)) {
                System.out.println("Campaña actualizada. (Si cambio nombre/fecha, el ID se actualizo)");
            } else {
                System.out.println("Error al actualizar. Revise el formato de fecha.");
            }
        } else {
            System.out.println("Campaña no encontrada.");
        }
    }

    private void editarExtraccion() {
        System.out.println("ID de la Campaña: "); String id = leer.nextLine();
        System.out.println("RUT actual del Donante: "); String rutV = leer.nextLine();

        Extraccion e = sistema.buscarExtraccion(rutV, id);
        if (e != null) {
            System.out.println("--- Presione Enter para mantener el valor actual ---");

            // Editar RUT
            System.out.println("Nuevo RUT [" + e.getVoluntario().getRut() + "]: ");
            String rutN = leer.nextLine();
            if (rutN.isEmpty()) rutN = e.getVoluntario().getRut();

            // Editar Fecha
            System.out.println("Nueva Fecha [" + e.getFechaExtraccion() + "]: ");
            String fecha = leer.nextLine();
            if (fecha.isEmpty()) fecha = e.getFechaExtraccion().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Editar Volumen
            System.out.println("Nuevo Volumen [" + e.getVolumenExtraido() + "]: ");
            String volStr = leer.nextLine();
            int vol = volStr.isEmpty() ? e.getVolumenExtraido() : Integer.parseInt(volStr);

            // Editar seSintioMal
            System.out.println("¿Se Sintio Mal? (actual: " + e.getSeSintioMal() + ") [true/false]: ");
            String malStr = leer.nextLine();
            boolean mal = malStr.isEmpty() ? e.getSeSintioMal() : Boolean.parseBoolean(malStr);

            if (sistema.actualizarExtraccion(rutV, id, rutN, fecha, vol, mal)) {
                System.out.println("Donacion actualizada correctamente.");
            } else {
                System.out.println("Error al actualizar. Verifique datos.");
            }
        } else {
            System.out.println("Extraccion no encontrada.");
        }
    }
    
    // --- MÉTODOS DE ELIMINACIÓN ---
    private void eliminarDonante() {
        System.out.println("RUT: ");
        if (sistema.eliminarDonante(leer.nextLine())) System.out.println("Borrado.");
    }

    private void eliminarCampana() {
        System.out.println("ID: ");
        if(sistema.eliminarCampaña(leer.nextLine()))
            System.out.println("Borrada.");
        else
            System.out.println("No se encontro la campaña especificada.");
    }

    private void eliminarExtraccion() {
        System.out.println("ID de la Campaña: "); String id = leer.nextLine();
        System.out.println("RUT del Donante: "); String rut = leer.nextLine();
        
        if (sistema.borrarExtraccion(id, rut)) {
            System.out.println("Donacion eliminada");
        } else {
            System.out.println("No se encontro la donacion..");
        }
    }
    
    private void restarStockManual() {
        System.out.println("\n--- RESTAR STOCK MANUAL ---");
        try {
            System.out.println("Ingrese Tipo de Sangre a corregir (ej: O+, A-): ");
            String tipoSangre = leer.nextLine().trim().toUpperCase(); 
            
            System.out.println("Ingrese Volumen a restar (en ml): ");
            int cantidad = Integer.parseInt(leer.nextLine());
            boolean exito = sistema.restarStockManual(tipoSangre, cantidad);
            
            if (exito) {
                System.out.println("¡Stock de sangre " + tipoSangre + " reducido correctamente!");
            } else {
                System.out.println("Error: El tipo de sangre '" + tipoSangre + "' no existe en el inventario.");
            }
            
        } catch (Exception e) {
            System.out.println("Error: Asegúrese de ingresar un número entero para el volumen.");
        }
    }
    
    // --- MÉTODOS DE LISTADO ---
    private void listarDonantes() {
        System.out.println("\n--- LISTADO DE DONANTES ---");
        List<String> resumenes = sistema.obtenerListaVoluntarios();
        if(resumenes.isEmpty()) {
            System.out.println("No hay donantes registrados.");
        } else {
            resumenes.forEach(donante ->
                System.out.println(donante));
        }
    }

    private void listarCampanas() {
        System.out.println("\n--- LISTADO DE CAMPAÑAS ---");
        List<String> resumenes = sistema.obtenerListaCampañas();
        if(resumenes.isEmpty()) {
            System.out.println("No hay campañas registradas.");
        } else {
            resumenes.forEach(resumenC ->
                System.out.println(resumenC));
        }
    }

    private void mostrarInventario() {
        System.out.println("\n---  INVENTARIO ---");
        List<String> inventarioResumen = sistema.obtenerResumenInventario();
        
        if(inventarioResumen.isEmpty()) {
            System.out.println("No hay extracciones registradas");
        } else {
            inventarioResumen.forEach(tipoResumen ->
                System.out.println(tipoResumen));
        }
    }

    private void listarExtraccionesPorCampaña() {
        System.out.println("Ingrese ID de la Campaña: ");
        String id = leer.nextLine();
        List<Extraccion> lista = sistema.getListaExtracciones(id); 
        
        if (lista != null && !lista.isEmpty()) {
            System.out.println("\n--- DONACIONES EN: " + id + " ---");
            for (Extraccion e : lista) {
                System.out.println("RUT: " + e.getVoluntario().getRut() + 
                                   " | Donante: " + e.getVoluntario().getNombre() + 
                                   " | Volumen: " + e.getVolumenExtraido() + "ml");
            }
        } else {
            System.out.println("No hay extracciones en esta campaña o el ID es invalido.");
        }
    }

    private void historialDonante() {
        System.out.println("Ingrese el RUT del donante: ");
        System.out.flush();
        String rut = leer.nextLine();

        if (sistema.buscarDonante(rut) == null) {
            System.out.println("El RUT " + rut + " no esta registrado.");
            return;
        }

        System.out.println("\n¿Que desea consultar?");
        System.out.println("1. Historial General (Todas las campañas)");
        System.out.println("2. Donacion Especifica (RUT + ID Campaña)");
        System.out.println("Seleccione una opcion: ");
        
        try {
            int subOp = Integer.parseInt(leer.nextLine());

            switch (subOp) {
                case 1:
                    List<InfoDonacion> historialCompleto = sistema.buscarExtraccion(rut);
                    if (historialCompleto.isEmpty()) {
                        System.out.println("El donante no registra extracciones aun.");
                    } else {
                        System.out.println("\n=== HISTORIAL GENERAL PARA EL RUT: " + rut + " ===");
                        for (InfoDonacion info : historialCompleto) {
                            System.out.println("- Campaña: " + info.getIdCampaña() + 
                                               " | Vol: " + info.getVolumenExtraido() + "ml" +
                                               " | Malestar: " + (info.getSeSintioMal() ? "SI" : "NO"));
                        }
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el ID de la Campaña: ");
                    String idC = leer.nextLine();
                    Extraccion e = sistema.buscarExtraccion(rut, idC);
                    
                    if (e != null) {
                        System.out.println("\n=== DETALLE DE DONACION EN " + idC + " ===");
                        System.out.println("Fecha: " + e.getFechaExtraccion());
                        System.out.println("Volumen Extraido: " + e.getVolumenExtraido() + " ml");
                        System.out.println("Reaccion adversa: " + (e.getSeSintioMal() ? "SI" : "NO"));
                    } else {
                        System.out.println("No se encontro una donacion de este RUT en la campaña " + idC);
                    }
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un numero valido.");
        }
    }
    
// Metodos Varios
    private void llamadoEmergencia() {
        System.out.println("\n--- GENERAR LLAMADO DE EMERGENCIA ---");
        System.out.println("Ingrese el tipo de sangre requerido (ej: O+, A-): ");
        String tipo = leer.nextLine().toUpperCase();

        // Se filtran los donantes aptos
        List<Donante> aptos = sistema.filtroTipoAntiguedad(tipo);

        if (aptos.isEmpty()) {
            System.out.println("No se encontraron donantes aptos de tipo " + tipo + " en este momento.");
            return;
        }

        System.out.println("Se encontraron " + aptos.size() + " donantes aptos.");
        System.out.println("Ingrese el nombre para el archivo de emergencia: ");
        String nombreArchivo = leer.nextLine();

        if (sistema.exportarListaAptos(aptos, nombreArchivo)) {
            System.out.println("Archivo '" + nombreArchivo + "' generado exitosamente.");
            
            System.out.println("\n¿Desea crear una Campaña Enfocada para este caso? (s/n): ");
            String respuesta = leer.nextLine().toLowerCase();
            
            if (respuesta.equals("s")) {
                crearCampanaEnfocada(tipo);
            }
        } else {
            System.out.println("❌ Error al exportar el archivo.");
        }
    }
    
    
}