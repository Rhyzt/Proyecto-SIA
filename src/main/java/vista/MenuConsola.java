package vista;

import entidades.Campaña;
import entidades.Donante;
import entidades.Extraccion;
import procesamiento.GestionHistorial;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

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
            System.out.println("\n--- SISTEMA GESTIÓN DE DONACIONES (SIA 2026) ---");
            // 1. REGISTROS
            System.out.println("1. Registrar Nuevo Donante");
            System.out.println("2. Crear Nueva Campaña");
            System.out.println("3. Registrar Donación (Extracción)");
            // 2. BÚSQUEDAS (SIA-8)
            System.out.println("4. Buscar Donante (RUT)");
            System.out.println("5. Buscar Campaña (ID)");
            // 3. EDICIONES (SIA-8)
            System.out.println("6. Editar Datos de Donante");
            System.out.println("7. Editar Datos de Campaña");
            // 4. ELIMINACIONES (SIA-8)
            System.out.println("8. Eliminar Donante");
            System.out.println("9. Eliminar Campaña");
            // 5. LISTADOS
            System.out.println("10. Listar Todos los Donantes");
            System.out.println("11. Listar Todas las Campañas");
            // 6. SISTEMA
            System.out.println("12. Ver Inventario General");
            System.out.println("13. Salir y Guardar");
            System.out.print("Seleccione una opción: ");

            try {
                op = Integer.parseInt(leer.nextLine());
                switch(op) {
                    case 1: registrarDonante(); break;
                    case 2: registrarCampana(); break;
                    case 3: registrarDonacion(); break;
                    case 4: buscarDonante(); break;
                    case 5: buscarCampana(); break;
                    case 6: editarDonante(); break;
                    case 7: editarCampana(); break;
                    case 8: eliminarDonante(); break;
                    case 9: eliminarCampana(); break;
                    case 10: listarDonantes(); break;
                    case 11: listarCampanas(); break;
                    case 12: mostrarInventario(); break;
                    case 13: System.out.println("Guardando datos..."); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (op != 13);
    }

    // --- MÉTODOS DE REGISTRO ---
    private void registrarDonante() {
        System.out.print("Nombre: "); String n = leer.nextLine();
        System.out.print("RUT: "); String r = leer.nextLine();
        System.out.print("Edad: "); int e = Integer.parseInt(leer.nextLine());
        System.out.print("Tipo Sangre: "); String t = leer.nextLine();
        System.out.print("Teléfono: "); String tel = leer.nextLine();
        if (sistema.agregarDonante(new Donante(n, r, e, "M", t, "01/01/2000", tel))) {
            System.out.println("Donante registrado.");
        }
    }

    private void registrarCampana() {
        System.out.print("Nombre: "); String n = leer.nextLine();
        System.out.print("Ubicación: "); String u = leer.nextLine();
        System.out.print("Fecha: "); String f = leer.nextLine();
        System.out.print("Meta (ml): "); int m = Integer.parseInt(leer.nextLine());
        if (sistema.agregarCampaña(new Campaña(n, u, f, m))) System.out.println("Campaña creada.");
    }

    private void registrarDonacion() {
        System.out.print("ID Campaña: "); String id = leer.nextLine();
        System.out.print("RUT Donante: "); String rut = leer.nextLine();
        Campaña c = sistema.buscarCampaña(id);
        Donante d = sistema.buscarDonante(rut);
        if (c != null && d != null) {
            System.out.print("Volumen (ml): ");
            int v = Integer.parseInt(leer.nextLine());
            sistema.registrarExtraccion(id, new Extraccion(d, c.getFechaCampaña(), v, false));
            System.out.println("Éxito.");
        }
    }

    // --- MÉTODOS DE BÚSQUEDA ---
    private void buscarDonante() {
        System.out.print("RUT: ");
        Donante d = sistema.buscarDonante(leer.nextLine());
        if (d != null) d.obtenerResumen();
        else System.out.println("No encontrado.");
    }

    private void buscarCampana() {
        System.out.print("ID: ");
        Campaña c = sistema.buscarCampaña(leer.nextLine());
        if (c != null) System.out.println("Nombre: " + c.getNombreCampaña() + " | Meta: " + c.getMetaDonaciones());
        else System.out.println("No encontrada.");
    }

    // --- MÉTODOS DE EDICIÓN ---
    private void editarDonante() {
        System.out.print("RUT: "); String r = leer.nextLine();
        Donante d = sistema.buscarDonante(r);
        if (d != null) {
            System.out.print("Nuevo Nombre: "); String n = leer.nextLine();
            System.out.print("Nuevo Tel: "); String t = leer.nextLine();
            sistema.actualizarDonante(r, n, d.getEdad(), d.getSexo(), d.getTipoSangre(), t);
            System.out.println("Actualizado.");
        }
    }

    private void editarCampana() {
        System.out.print("ID: "); String id = leer.nextLine();
        Campaña c = sistema.buscarCampaña(id);
        if (c != null) {
            System.out.print("Nuevo Nombre: "); String n = leer.nextLine();
            System.out.print("Nueva Fecha: "); String f = leer.nextLine();
            sistema.actualizarCampaña(id, n, c.getUbicacion(), f, c.getMetaDonaciones());
            System.out.println("Editada.");
        }
    }

    // --- MÉTODOS DE ELIMINACIÓN ---
    private void eliminarDonante() {
        System.out.print("RUT: ");
        if (sistema.eliminarDonante(leer.nextLine())) System.out.println("Borrado.");
    }

    private void eliminarCampana() {
        System.out.print("ID: ");
        if (sistema.eliminarCampaña(leer.nextLine())) System.out.println("Borrada.");
    }

    // --- MÉTODOS DE LISTADO ---
    private void listarDonantes() {
        System.out.println("\n--- LISTADO DE DONANTES ---");
        sistema.getVoluntarios().values().forEach(d ->
                System.out.println(d.getRut() + " | " + d.getNombre() + " | " + d.getTipoSangre()));
    }

    private void listarCampanas() {
        System.out.println("\n--- LISTADO DE CAMPAÑAS ---");
        sistema.getCampañas().forEach(c ->
                System.out.println(c.getIdCampaña() + " | Meta: " + c.getMetaDonaciones() + "ml"));
    }

    private void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ---");
        sistema.getInv().getStockSangre().forEach((k, v) -> System.out.println(k + ": " + v + "ml"));
    }
}