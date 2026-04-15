package vista;

import archivos.ArchivoUtil;
import javax.swing.JOptionPane;
import procesamiento.GestionHistorial;

public class Main {
    public static void main(String[] args) {
        // 1. Inicialización del Sistema (SIA-3 / SIA-4)
        // Se crea el objeto que contiene todas las colecciones (Maps y Listas)
        GestionHistorial sistema = new GestionHistorial();

        // 2. Carga de datos al iniciar (SIA-11: Sistema Batch)
        // Antes de que el usuario vea nada, cargamos los archivos .csv si existen
        System.out.println("Cargando base de datos...");
        ArchivoUtil.cargarTodo(sistema);

        // 3. Selección de Interfaz (SIA-10: Interfaz Dual)
        // Se cumple el requisito de decidir modo de ejecución al lanzar la app
        String[] opciones = {"Consola", "Ventana"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el modo de ejecución para el sistema de donaciones:",
                "SIA 2026 - Inicio",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        // 4. Ejecución del flujo según elección
        try {
            if (seleccion == 0) {
                System.out.println("--- INICIANDO MODO CONSOLA ---");
                // Instanciamos el menú que maneja el Scanner (SIA-7, SIA-8)
                MenuConsola menu = new MenuConsola(sistema);
                menu.desplegar(); // Este método mantiene el programa vivo
            } else if (seleccion == 1) {
                System.out.println("--- INICIANDO MODO VENTANA ---");
                // Abrimos la interfaz gráfica de Swing (SIA-10)
                VentanaPrincipal ventana = new VentanaPrincipal(sistema);
                ventana.setVisible(true);

                // Nota: En Swing el programa no termina hasta cerrar la ventana
            } else {
                System.out.println("Ejecución cancelada.");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error en la ejecución: " + e.getMessage());
        } finally {
            // 5. Guardado de datos al salir (SIA-11: Sistema Batch)
            // El bloque finally garantiza que los datos se graben en los .csv
            // aunque el programa falle o el usuario elija salir.
            System.out.println("Finalizando y guardando cambios en CSV...");
            ArchivoUtil.guardarTodo(sistema);
            System.out.println("Sistema cerrado correctamente.");
        }
    }
}