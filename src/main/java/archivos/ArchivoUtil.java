package archivos;

import entidades.Campaña;
import entidades.CampañaEnfocada;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import procesamiento.GestionHistorial;
import entidades.Donante;
import entidades.Extraccion;

public class ArchivoUtil {
    private static final String FILE_CAMPANAS = "csv/campañas.csv";
    private static final String FILE_DONANTES = "csv/donantes.csv";
    private static final String FILE_EXTRACCIONES = "csv/extracciones.csv";
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void cargarTodo(GestionHistorial sistema) {
        cargarDonantes(sistema);
        cargarCampanas(sistema);
        cargarExtracciones(sistema);
    }

    private static void cargarDonantes(GestionHistorial sistema) {
        File f = new File(FILE_DONANTES);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length < 7) continue;
                Donante donante = new Donante(d[1], d[0], Integer.parseInt(d[2]), d[3], d[4], d[5], d[6]);
                sistema.agregarDonante(donante);
            }
        } catch (Exception e) { System.err.println("Error donantes: " + e.getMessage()); }
    }

    private static void cargarCampanas(GestionHistorial sistema) {
        File f = new File(FILE_CAMPANAS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {   
                String[] d = linea.split(",");
                if (d.length < 5) continue;
                
                Campaña c;
                // Si tiene 7 o más columnas, es una CampañaEnfocada
                if (d.length >= 7) {
                    c = new CampañaEnfocada(
                        d[1], 
                        d[2], 
                        d[3], 
                        Integer.parseInt(d[4]), 
                        d[5], 
                        Float.parseFloat(d[6])  
                    );
                } else {
                    // Es una campaña normal
                    c = new Campaña(d[1], d[2], d[3], Integer.parseInt(d[4]));
                }
                
                c.setIdCampaña(d[0]); // Settear el id al real
                sistema.agregarCampaña(c); 
            }
        } catch (Exception e) { System.err.println("Error campañas: " + e.getMessage()); }
    }

    private static void cargarExtracciones(GestionHistorial sistema) {
        File f = new File(FILE_EXTRACCIONES);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                Campaña c = sistema.buscarCampaña(d[0]);
                Donante v = sistema.buscarDonante(d[1]);
                if (c != null && v != null) {
                    Extraccion ex = new Extraccion(v, c.getFechaCampaña(), Integer.parseInt(d[2]), Boolean.parseBoolean(d[3]));
                    sistema.registrarExtraccion(c.getIdCampaña(), ex);
                }
            }
        } catch (Exception e) { System.err.println("Error extracciones: " + e.getMessage()); }
    }

    public static void guardarTodo(GestionHistorial sistema) {
        guardarDonantes(sistema.getVoluntarios());
        guardarCampanas(sistema.getCampañas());
        guardarExtracciones(sistema.getHistorial());
    }

    private static void guardarDonantes(Map<String, Donante> voluntarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_DONANTES))) {
            for (Donante d : voluntarios.values()) {
                pw.println(d.getRut() + "," + d.getNombre() + "," + d.getEdad() + "," +
                        d.getSexo() + "," + d.getTipoSangre() + "," +
                        d.getFechaUltimaDonacion().format(fmt) + "," + d.getTelefono());
            }
        } catch (IOException e) { System.err.println("Error guardando donantes: " + e.getMessage()); }
    }

    private static void guardarCampanas(List<Campaña> campañas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_CAMPANAS))) {
            for (Campaña c : campañas) {
                String linea = c.getIdCampaña() + "," + c.getNombreCampaña() + "," +
                               c.getUbicacion() + "," + c.getFechaCampaña().format(fmt) +
                               "," + c.getMetaDonaciones();

                // Si es enfocada, agregamos sus campos únicos
                if (c instanceof CampañaEnfocada) {
                    CampañaEnfocada ce = (CampañaEnfocada) c;
                    linea += "," + ce.getGrupoObjetivo() + "," + ce.getPorcentajeMeta();
                }
                pw.println(linea);
            }
        } catch (IOException e) { System.err.println("Error guardando campañas: " + e.getMessage()); }
    } 

    private static void guardarExtracciones(Map<String, List<Extraccion>> historial) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_EXTRACCIONES))) {
            for (String idCampaña : historial.keySet()) {
                for (Extraccion e : historial.get(idCampaña)) {
                    pw.println(idCampaña + "," + e.getVoluntario().getRut() + "," +
                            e.getVolumenExtraido() + "," + e.getSeSintioMal());
                }
            }
        } catch (IOException e) { System.err.println("Error guardando extracciones: " + e.getMessage()); }
    }
}