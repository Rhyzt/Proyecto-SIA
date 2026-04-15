import java.util.Scanner;

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
            System.out.println("1. Registrar Nuevo Donante");
            System.out.println("2. Crear Nueva Campaña");
            System.out.println("3. Registrar Donación (Extracción)");
            System.out.println("4. Listar Campañas y Donantes");
            System.out.println("5. Ver Inventario y Alertas de Stock");
            System.out.println("6. Salir y Guardar");
            System.out.print("Seleccione una opción: ");

            try {
                op = Integer.parseInt(leer.nextLine()); // Usar parseInt evita problemas con el buffer
                switch(op) {
                    case 1: registrarDonante(); break;
                    case 2: registrarCampaña(); break;
                    case 3: registrarDonacion(); break;
                    case 4:
                        sistema.listarCampañas();
                        // Puedes añadir un listarDonantes si lo deseas
                        break;
                    case 5:
                        sistema.getInv().mostrarInventario();
                        sistema.getInv().mostrarInventario(1000); // SIA-5: Sobrecarga
                        break;
                    case 6: System.out.println("Guardando cambios y cerrando..."); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (op != 6);
    }

    // --- REGISTRO DE DONANTES (FUNCIONALIDAD SOLICITADA) ---
    private void registrarDonante() {
        try {
            System.out.println("\n--- NUEVO DONANTE ---");
            System.out.print("Nombre completo: ");
            String nombre = leer.nextLine();
            System.out.print("RUT (sin puntos ni guion): ");
            String rut = leer.nextLine();
            System.out.print("Edad: ");
            int edad = Integer.parseInt(leer.nextLine());

            // SIA-12: Validación con excepción personalizada
            if (edad < 18 || edad > 65) throw new EdadNoValidaException(edad);

            System.out.print("Sexo (M/F/Otro): ");
            String sexo = leer.nextLine();
            System.out.print("Tipo de Sangre (ej: O+, A-): ");
            String tipo = leer.nextLine().toUpperCase();
            System.out.print("Teléfono: ");
            String tel = leer.nextLine();

            // Se crea el objeto con fecha de última donación antigua para permitir donar de inmediato
            Donante nuevo = new Donante(nombre, rut, edad, sexo, tipo, "01/01/2000", tel);

            if (sistema.agregarDonante(nuevo)) {
                System.out.println(">>> Donante registrado exitosamente.");
            } else {
                System.out.println(">>> El donante ya existe en el sistema.");
            }
        } catch (EdadNoValidaException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: Datos inválidos.");
        }
    }

    // --- MANEJO DE CAMPAÑAS (FUNCIONALIDAD SOLICITADA) ---
    private void registrarCampaña() {
        try {
            System.out.println("\n--- NUEVA CAMPAÑA ---");
            System.out.print("Nombre de la campaña: ");
            String nombre = leer.nextLine();
            System.out.print("Ubicación: ");
            String ub = leer.nextLine();
            System.out.print("Fecha (dd/mm/yyyy): ");
            String fecha = leer.nextLine();
            System.out.print("Meta de donación (ml): ");
            int meta = Integer.parseInt(leer.nextLine());

            Campaña nueva = new Campaña(nombre, ub, fecha, meta);
            if (sistema.agregarCampaña(nueva)) {
                System.out.println(">>> Campaña creada con ID: " + nueva.getIdCampaña());
            } else {
                System.out.println(">>> La campaña ya existe.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Formato de datos incorrecto.");
        }
    }

    // --- SEGUIMIENTO DE DISPONIBILIDAD (REGISTRO DE EXTRACCIÓN) ---
    private void registrarDonacion() {
        try {
            System.out.print("ID de la Campaña: ");
            String idC = leer.nextLine();
            System.out.print("RUT del Donante: ");
            String rut = leer.nextLine();

            Campaña c = sistema.buscarCampaña(idC);
            Donante d = sistema.buscarDonante(rut);

            if (c != null && d != null) {
                if (d.esAptoParaDonar(c.getFechaCampaña())) {
                    System.out.print("Volumen (ml): ");
                    int vol = Integer.parseInt(leer.nextLine());
                    System.out.print("¿Hubo malestar? (true/false): ");
                    boolean mal = Boolean.parseBoolean(leer.nextLine());

                    Extraccion ex = new Extraccion(d, c.getFechaCampaña(), vol, mal);
                    sistema.registrarExtraccion(idC, ex);
                    System.out.println(">>> Donación registrada y stock actualizado.");
                }
            } else {
                System.out.println(">>> Campaña o Donante no encontrados.");
            }
        } catch (FrecuenciaDonacionException e) {
            System.out.println("RECHAZADO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: Datos inválidos.");
        }
    }
}