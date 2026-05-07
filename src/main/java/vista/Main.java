package vista;

import archivos.ArchivoUtil;
import javax.swing.JOptionPane;
import procesamiento.GestionHistorial;

public class Main {
    public static void main(String[] args) {
        // 1. Inicialización del Sistema
        // Se crea el objeto que contiene todas las colecciones (Maps y Listas)
        GestionHistorial sistema = new GestionHistorial();

        // 2. Carga de datos al iniciar
        System.out.println("Cargando base de datos...");
        ArchivoUtil.cargarTodo(sistema);

        // 3. Selección de Interfaz
        String[] opciones = {"Consola", "Ventana"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el modo de ejecución para el sistema de donaciones:",
                "Inicio",
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
                try { // Bloque para la ejecucion de la consola
                    MenuConsola menu = new MenuConsola(sistema);
                    menu.desplegar(); 
                } finally { // Finally para proteger los datos en caso de un error grave
                    System.out.println("Finalizando y guardando cambios en CSV...");
                    ArchivoUtil.guardarTodo(sistema);
                    System.out.println("Sistema cerrado correctamente.");
                }
            } else if (seleccion == 1) { // La ventana tiene su propia seguridad
                System.out.println("--- INICIANDO MODO VENTANA ---");
                VentanaPrincipal ventana = new VentanaPrincipal(sistema);
                ventana.setVisible(true);
            } else {
                System.out.println("Ejecución cancelada.");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error en la ejecución: " + e.getMessage());
        }
    }
}