package vista;

import entidades.Campaña;
import entidades.Donante;
import entidades.Extraccion;
import exceptions.EdadNoValidaException;
import exceptions.FrecuenciaDonacionException;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import procesamiento.GestionHistorial;

public class VentanaPrincipal extends JFrame {
    private GestionHistorial sistema;

    public VentanaPrincipal(GestionHistorial sistema) {
        this.sistema = sistema;
        setTitle("SIA - Gestión de Donaciones de Sangre");
        setSize(500, 600); // Tamaño ajustado para todas las funciones
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Diseño de rejilla para 8 botones (agregamos eliminar y listar donantes)
        setLayout(new GridLayout(8, 1, 10, 10));

        // --- Definición de Botones ---
        JButton btnRegDonante = new JButton("1. Registrar Nuevo Donante");
        JButton btnCrearCampana = new JButton("2. Crear Nueva Campaña");
        JButton btnRegExtraccion = new JButton("3. Registrar Donación (Extracción)");
        JButton btnListarDonantes = new JButton("4. Ver Listado de Donantes");
        JButton btnListarCampanas = new JButton("5. Ver Listado de Campañas");
        JButton btnInventario = new JButton("6. Ver Inventario y Stock");
        JButton btnEliminarCampana = new JButton("7. Eliminar Campaña (Baja de Stock)");
        JButton btnSalir = new JButton("8. Salir y Guardar");

        // --- LÓGICA DE CADA BOTÓN (Reflejo de MenuConsola) ---

        // 1. Registro de Donantes (SIA-7 / SIA-12)
        btnRegDonante.addActionListener(e -> {
            try {
                String nombre = JOptionPane.showInputDialog(this, "Nombre Completo:");
                if (nombre == null) return;
                String rut = JOptionPane.showInputDialog(this, "RUT (sin puntos ni guion):");
                int edad = Integer.parseInt(JOptionPane.showInputDialog(this, "Edad:"));

                if (edad < 18 || edad > 65) throw new EdadNoValidaException(edad);

                String tipo = JOptionPane.showInputDialog(this, "Tipo de Sangre (ej: O+):").toUpperCase();
                String tel = JOptionPane.showInputDialog(this, "Teléfono de contacto:");

                Donante nuevo = new Donante(nombre, rut, edad, "M", tipo, "01/01/2000", tel);
                if (sistema.agregarDonante(nuevo)) {
                    JOptionPane.showMessageDialog(this, "Donante registrado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "El RUT ya existe en el sistema.");
                }
            } catch (EdadNoValidaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Edad", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos o campos vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2. Crear Campaña (SIA-7)
        btnCrearCampana.addActionListener(e -> {
            try {
                String nombre = JOptionPane.showInputDialog(this, "Nombre de la Campaña:");
                String ub = JOptionPane.showInputDialog(this, "Ubicación:");
                String fecha = JOptionPane.showInputDialog(this, "Fecha (dd/mm/yyyy):");
                int meta = Integer.parseInt(JOptionPane.showInputDialog(this, "Meta de donación (ml):"));

                Campaña nueva = new Campaña(nombre, ub, fecha, meta);
                if (sistema.agregarCampaña(nueva)) {
                    JOptionPane.showMessageDialog(this, "Campaña creada con ID: " + nueva.getIdCampaña());
                } else {
                    JOptionPane.showMessageDialog(this, "La campaña ya existe.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en el formato de los datos.");
            }
        });

        // 3. Registrar Donación (Extracción) -> Conecta Donante, Campaña e Inventario
        btnRegExtraccion.addActionListener(e -> {
            try {
                String idC = JOptionPane.showInputDialog(this, "ID de la Campaña:");
                String rut = JOptionPane.showInputDialog(this, "RUT del Donante:");

                Campaña c = sistema.buscarCampaña(idC);
                Donante d = sistema.buscarDonante(rut);

                if (c != null && d != null) {
                    if (d.esAptoParaDonar(c.getFechaCampaña())) {
                        int vol = Integer.parseInt(JOptionPane.showInputDialog(this, "Volumen extraído (ml):"));
                        int malestar = JOptionPane.showConfirmDialog(this, "¿Sintió malestar?", "Reacción", JOptionPane.YES_NO_OPTION);

                        Extraccion ex = new Extraccion(d, c.getFechaCampaña(), vol, malestar == JOptionPane.YES_OPTION);
                        sistema.registrarExtraccion(idC, ex);
                        JOptionPane.showMessageDialog(this, "Donación exitosa. Stock de " + d.getTipoSangre() + " actualizado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Campaña o Donante no encontrados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FrecuenciaDonacionException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aptitud Médica", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en el ingreso de datos.");
            }
        });

        // 4. Listar Donantes (SIA-7)
        btnListarDonantes.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- DONANTES REGISTRADOS ---\n");
            sistema.getVoluntarios().values().forEach(d ->
                    sb.append("Nombre: ").append(d.getNombre()).append(" | RUT: ").append(d.getRut()).append(" | Tipo: ").append(d.getTipoSangre()).append("\n")
            );
            mostrarScroll(sb.toString(), "Listado de Donantes");
        });

        // 5. Listar Campañas (SIA-7)
        btnListarCampanas.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("--- CAMPAÑAS ACTIVAS ---\n");
            sistema.getCampañas().forEach(c ->
                    sb.append("ID: ").append(c.getIdCampaña()).append(" | Meta: ").append(c.getMetaDonaciones()).append("ml\n")
            );
            mostrarScroll(sb.toString(), "Listado de Campañas");
        });

        // 6. Inventario (SIA-4)
        btnInventario.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, sistema.getInv().obtenerInventarioString(), "Inventario Actual", JOptionPane.INFORMATION_MESSAGE);
        });

        // 7. Eliminar Campaña (SIA-8) -> Refleja cambios en el stock
        btnEliminarCampana.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de la campaña a eliminar:");
            if (id != null && sistema.eliminarCampaña(id)) {
                JOptionPane.showMessageDialog(this, "Campaña eliminada y stock revertido con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la campaña.");
            }
        });

        // 8. Salir y Guardar (SIA-11)
        btnSalir.addActionListener(e -> dispose());

        // --- Agregar componentes al panel ---
        add(btnRegDonante); add(btnCrearCampana); add(btnRegExtraccion);
        add(btnListarDonantes); add(btnListarCampanas); add(btnInventario);
        add(btnEliminarCampana); add(btnSalir);
    }

    /**
     * Método auxiliar para mostrar textos largos en ventanas con scroll.
     */
    private void mostrarScroll(String texto, String titulo) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}