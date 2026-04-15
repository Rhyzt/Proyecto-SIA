package vista;

import entidades.Campaña;
import entidades.Donante;
import exceptions.EdadNoValidaException;
import javax.swing.*;
import java.awt.*;
import procesamiento.GestionHistorial;

public class VentanaPrincipal extends JFrame {
    private GestionHistorial sistema;

    public VentanaPrincipal(GestionHistorial sistema) {
        this.sistema = sistema;
        setTitle("SIA - Gestión de Donaciones de Sangre");
        setSize(450, 500); // Aumentamos tamaño para los nuevos botones
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 10, 10)); // Cambiamos a 7 espacios

        // Botones de Registro (Lo que te falta)
        JButton btnRegistrarDonante = new JButton("Registrar Nuevo Donante");
        JButton btnCrearCampana = new JButton("Crear Nueva Campaña");

        // Botones de Consulta (Lo que ya tienes)
        JButton btnListar = new JButton("Listar Campañas");
        JButton btnInventario = new JButton("Ver Inventario de Sangre");
        JButton btnBuscar = new JButton("Buscar Donante por RUT");
        JButton btnSalir = new JButton("Salir y Guardar");

        // --- LÓGICA DE REGISTRO DE DONANTE (SIA-7 / SIA-12) ---
        btnRegistrarDonante.addActionListener(e -> {
            try {
                String nombre = JOptionPane.showInputDialog(this, "Nombre Completo:");
                if (nombre == null) return;
                String rut = JOptionPane.showInputDialog(this, "RUT (sin puntos ni guion):");
                String edadStr = JOptionPane.showInputDialog(this, "Edad:");
                int edad = Integer.parseInt(edadStr);

                // Validación SIA-12
                if (edad < 18 || edad > 65) throw new EdadNoValidaException(edad);

                String tipo = JOptionPane.showInputDialog(this, "Tipo de Sangre (ej: O+):").toUpperCase();

                Donante nuevo = new Donante(nombre, rut, edad, "M", tipo, "01/01/2000", "999999");
                if (sistema.agregarDonante(nuevo)) {
                    JOptionPane.showMessageDialog(this, "Donante registrado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "El RUT ya existe.");
                }
            } catch (EdadNoValidaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- LÓGICA DE CREAR CAMPAÑA (SIA-7) ---
        btnCrearCampana.addActionListener(e -> {
            try {
                String nombre = JOptionPane.showInputDialog(this, "Nombre de Campaña:");
                String ub = JOptionPane.showInputDialog(this, "Ubicación:");
                String fecha = JOptionPane.showInputDialog(this, "Fecha (dd/mm/yyyy):");
                int meta = Integer.parseInt(JOptionPane.showInputDialog(this, "Meta (ml):"));

                Campaña nueva = new Campaña(nombre, ub, fecha, meta);
                if (sistema.agregarCampaña(nueva)) {
                    JOptionPane.showMessageDialog(this, "Campaña creada: " + nueva.getIdCampaña());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos.");
            }
        });

        // Acciones que ya tenías (resumidas)
        btnListar.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Campañas:\n");
            sistema.getCampañas().forEach(c -> sb.append("- ").append(c.getNombreCampaña()).append("\n"));
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnInventario.addActionListener(e ->
                JOptionPane.showMessageDialog(this, sistema.getInv().obtenerInventarioString())
        );

        btnSalir.addActionListener(e -> dispose());

        // Agregar todo a la ventana
        add(btnRegistrarDonante);
        add(btnCrearCampana);
        add(btnListar);
        add(btnInventario);
        add(btnBuscar);
        add(btnSalir);
    }
}